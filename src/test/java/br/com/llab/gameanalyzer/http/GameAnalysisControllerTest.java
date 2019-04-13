package br.com.llab.gameanalyzer.http;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.domains.Kill;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToFindGamesException;
import br.com.llab.gameanalyzer.gateways.exceptions.GameNotFoundException;
import br.com.llab.gameanalyzer.usecases.QueryGameAnalysis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = { GameAnalysisController.class})
public class GameAnalysisControllerTest {

    private static final String URL_PATH = "/api/game/analysis";
    private static final String URL_PATH_GAME_NUMBER = "/api/game/analysis/{gameNumber}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QueryGameAnalysis queryGameAnalysis;


    @Test public void shouldQueryGameAnalysisSuccessfully()  throws Exception{
        when(queryGameAnalysis.query()).thenReturn(getListGame());

        final ResultActions resultActions = mockMvc.perform(get(URL_PATH));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("[0].game_number", is(1)))
                .andExpect(jsonPath("[0].total_kills", is(17)))
                .andExpect(jsonPath("[0].players", hasSize(2)))
                .andExpect(jsonPath("[0].players[0]", is("JrMatador")))
                .andExpect(jsonPath("[0].players[1]", is("MKiller")))
                .andExpect(jsonPath("[0].kills", hasSize(2)))
                .andExpect(jsonPath("[0].kills[0].player_name", is("JrMatador")))
                .andExpect(jsonPath("[0].kills[0].kill_number", is(7)))
                .andExpect(jsonPath("[0].kills[1].player_name", is("MKiller")))
                .andExpect(jsonPath("[0].kills[1].kill_number", is(10)))

                .andExpect(jsonPath("[1].game_number", is(2)))
                .andExpect(jsonPath("[1].total_kills", is(14)))
                .andExpect(jsonPath("[1].players", hasSize(3)))
                .andExpect(jsonPath("[1].players[0]", is("Everykill")))
                .andExpect(jsonPath("[1].players[1]", is("ForlixKill")))
                .andExpect(jsonPath("[1].players[2]", is("Shooter")))
                .andExpect(jsonPath("[1].kills", hasSize(3)))
                .andExpect(jsonPath("[1].kills[0].player_name", is("Everykill")))
                .andExpect(jsonPath("[1].kills[0].kill_number", is(4)))
                .andExpect(jsonPath("[1].kills[1].player_name", is("ForlixKill")))
                .andExpect(jsonPath("[1].kills[1].kill_number", is(7)))
                .andExpect(jsonPath("[1].kills[2].player_name", is("Shooter")))
                .andExpect(jsonPath("[1].kills[2].kill_number", is(3)));


    }

    @Test public void shouldQueryGameAnalysisException()  throws Exception{
        when(queryGameAnalysis.query()).thenThrow(new ErrorToFindGamesException());

        final ResultActions resultActions = mockMvc.perform(get(URL_PATH));

        resultActions.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot find any game!")));
    }

    @Test public void shouldQueryGameAnalysisNotFoundException()  throws Exception{
        when(queryGameAnalysis.query()).thenThrow(new GameNotFoundException());

        final ResultActions resultActions = mockMvc.perform(get(URL_PATH));

        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test public void shouldQueryGameAnalysisByGameNumberSuccessfully()  throws Exception{
        when(queryGameAnalysis.query(2)).thenReturn(getListGame().get(1));

        final ResultActions resultActions = mockMvc.perform(get(URL_PATH_GAME_NUMBER, 2));

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.game_number", is(2)))
                .andExpect(jsonPath("$.total_kills", is(14)))
                .andExpect(jsonPath("$.players", hasSize(3)))
                .andExpect(jsonPath("$.players[0]", is("Everykill")))
                .andExpect(jsonPath("$.players[1]", is("ForlixKill")))
                .andExpect(jsonPath("$.players[2]", is("Shooter")))
                .andExpect(jsonPath("$.kills", hasSize(3)))
                .andExpect(jsonPath("$.kills[0].player_name", is("Everykill")))
                .andExpect(jsonPath("$.kills[0].kill_number", is(4)))
                .andExpect(jsonPath("$.kills[1].player_name", is("ForlixKill")))
                .andExpect(jsonPath("$.kills[1].kill_number", is(7)))
                .andExpect(jsonPath("$.kills[2].player_name", is("Shooter")))
                .andExpect(jsonPath("$.kills[2].kill_number", is(3)));


    }

    @Test public void shouldQueryGameAnalysisByGameNumberNotFound()  throws Exception{
        when(queryGameAnalysis.query(2)).thenThrow(new GameNotFoundException());

        final ResultActions resultActions = mockMvc.perform(get(URL_PATH_GAME_NUMBER, 2));

        resultActions.andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test public void shouldQueryGameAnalysisByGameNumberException()  throws Exception{
        when(queryGameAnalysis.query(2)).thenThrow(new ErrorToFindGamesException());

        final ResultActions resultActions = mockMvc.perform(get(URL_PATH_GAME_NUMBER, 2));

        resultActions.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Cannot find any game!")));
    }

    private List<Game> getListGame() {
        List<Game> gameList = new ArrayList<>();

        List<Kill> killList = new ArrayList<>();

        killList.add(new Kill("1", "JrMatador", 7));
        killList.add(new Kill("2", "MKiller", 10));

        Game game = new Game();
        game.setGameNumber(1);
        game.setPlayers(Arrays.asList("JrMatador", "MKiller"));
        game.setKills(killList);
        game.setTotalKills(17);

        gameList.add(game);

        killList = new ArrayList<>();

        killList.add(new Kill("5", "Everykill", 4));
        killList.add(new Kill("6", "ForlixKill", 7));
        killList.add(new Kill("7", "Shooter", 3));

        game = new Game();
        game.setGameNumber(2);
        game.setPlayers(Arrays.asList("Everykill", "ForlixKill", "Shooter"));
        game.setKills(killList);
        game.setTotalKills(14);

        gameList.add(game);

        return gameList;
    }
}
