package br.com.llab.gameanalyzer.gateways.mongo;

import br.com.llab.gameanalyzer.configs.FongoConfiguration;
import br.com.llab.gameanalyzer.domains.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {FongoConfiguration.class})
public class GameDatabaseMongoGatewayIntTest {

    @Autowired
    private GameRopository repository;

    @Test public void shouldSaveAndFindAllGamesSuccessfully() {

        List<Game> gameList = getListGame();

        repository.saveAll(gameList);

        List<Game> result = repository.findAll();

        assertEquals(2, result.size());

        assertEquals(1, result.get(0).getGameNumber());
        assertEquals(17, result.get(0).getTotalKills());

        assertEquals(3, result.get(0).getPlayers().size());
        assertEquals("JrMatador", result.get(0).getPlayers().get(0));
        assertEquals("MKiller", result.get(0).getPlayers().get(1));
        assertEquals("SniperCerto", result.get(0).getPlayers().get(2));

        assertEquals(3, result.get(0).getKills().size());
        assertEquals(Integer.valueOf(5), result.get(0).getKills().get("JrMatador"));
        assertEquals(Integer.valueOf(10), result.get(0).getKills().get("MKiller"));
        assertEquals(Integer.valueOf(2), result.get(0).getKills().get("SniperCerto"));

        assertEquals(2, result.get(1).getGameNumber());
        assertEquals(14, result.get(1).getTotalKills());

        assertEquals(3, result.get(1).getPlayers().size());
        assertEquals("Everykill", result.get(1).getPlayers().get(0));
        assertEquals("ForlixKill", result.get(1).getPlayers().get(1));
        assertEquals("Shooter", result.get(1).getPlayers().get(2));

        assertEquals(3, result.get(1).getKills().size());
        assertEquals(Integer.valueOf(4), result.get(1).getKills().get("Everykill"));
        assertEquals(Integer.valueOf(7), result.get(1).getKills().get("ForlixKill"));
        assertEquals(Integer.valueOf(3), result.get(1).getKills().get("Shooter"));
    }

    private List<Game> getListGame() {
        List<Game> gameList = new ArrayList<>();

        Map<String, Integer> mapKills = new HashMap<>();
        mapKills.put("JrMatador", 5);
        mapKills.put("MKiller", 10);
        mapKills.put("SniperCerto", 2);

        Game game = new Game();
        game.setGameNumber(1);
        game.setPlayers(Arrays.asList("JrMatador", "MKiller", "SniperCerto"));
        game.setKills(mapKills);
        game.setTotalKills(17);

        gameList.add(game);

        mapKills = new HashMap<>();
        mapKills.put("Everykill", 4);
        mapKills.put("ForlixKill", 7);
        mapKills.put("Shooter", 3);

        game = new Game();
        game.setGameNumber(2);
        game.setPlayers(Arrays.asList("Everykill", "ForlixKill", "Shooter"));
        game.setKills(mapKills);
        game.setTotalKills(14);

        gameList.add(game);

        return gameList;
    }
}