package br.com.llab.gameanalyzer.gateways.mongo;

import br.com.llab.gameanalyzer.configs.FongoConfiguration;
import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.domains.Kill;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {FongoConfiguration.class})
public class GameDatabaseMongoGatewayIntTest {

    @Autowired
    private GameRopository repository;

    @Before
    public void initMocks() {
        this.repository.deleteAll();
    }

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

        assertEquals("JrMatador", result.get(0).getKills().get(0).getPlayer());
        assertEquals(Integer.valueOf(5), result.get(0).getKills().get(0).getKillNumber());
        assertEquals("1", result.get(0).getKills().get(0).getIdPlayer());

        assertEquals("MKiller", result.get(0).getKills().get(1).getPlayer());
        assertEquals(Integer.valueOf(10), result.get(0).getKills().get(1).getKillNumber());
        assertEquals("2", result.get(0).getKills().get(1).getIdPlayer());

        assertEquals("SniperCerto", result.get(0).getKills().get(2).getPlayer());
        assertEquals(Integer.valueOf(2), result.get(0).getKills().get(2).getKillNumber());
        assertEquals("3", result.get(0).getKills().get(2).getIdPlayer());

        assertEquals(2, result.get(1).getGameNumber());
        assertEquals(14, result.get(1).getTotalKills());

        assertEquals(3, result.get(1).getPlayers().size());
        assertEquals("Everykill", result.get(1).getPlayers().get(0));
        assertEquals("ForlixKill", result.get(1).getPlayers().get(1));
        assertEquals("Shooter", result.get(1).getPlayers().get(2));

        assertEquals(3, result.get(1).getKills().size());

        assertEquals("Everykill", result.get(1).getKills().get(0).getPlayer());
        assertEquals(Integer.valueOf(4), result.get(1).getKills().get(0).getKillNumber());
        assertEquals("5", result.get(1).getKills().get(0).getIdPlayer());

        assertEquals("ForlixKill", result.get(1).getKills().get(1).getPlayer());
        assertEquals(Integer.valueOf(7), result.get(1).getKills().get(1).getKillNumber());
        assertEquals("6", result.get(1).getKills().get(1).getIdPlayer());

        assertEquals("Shooter", result.get(1).getKills().get(2).getPlayer());
        assertEquals(Integer.valueOf(3), result.get(1).getKills().get(2).getKillNumber());
        assertEquals("7", result.get(1).getKills().get(2).getIdPlayer());
    }

    @Test
    public void shouldFindGameByNumberSuccessfully() {

        List<Game> gameList = getListGame();

        repository.saveAll(gameList);

        Optional<Game> resultOptional = repository.findByGameNumber(2);

        assertTrue(resultOptional.isPresent());

        Game result = resultOptional.get();

        assertEquals(2, result.getGameNumber());
        assertEquals(14, result.getTotalKills());

        assertEquals(3, result.getPlayers().size());
        assertEquals("Everykill", result.getPlayers().get(0));
        assertEquals("ForlixKill", result.getPlayers().get(1));
        assertEquals("Shooter", result.getPlayers().get(2));

        assertEquals(3, result.getKills().size());

        assertEquals("Everykill", result.getKills().get(0).getPlayer());
        assertEquals(Integer.valueOf(4), result.getKills().get(0).getKillNumber());
        assertEquals("5", result.getKills().get(0).getIdPlayer());

        assertEquals("ForlixKill", result.getKills().get(1).getPlayer());
        assertEquals(Integer.valueOf(7), result.getKills().get(1).getKillNumber());
        assertEquals("6", result.getKills().get(1).getIdPlayer());

        assertEquals("Shooter", result.getKills().get(2).getPlayer());
        assertEquals(Integer.valueOf(3), result.getKills().get(2).getKillNumber());
        assertEquals("7", result.getKills().get(2).getIdPlayer());
    }

    @Test
    public void shouldDeleteAllGamesSuccessfully() {
        List<Game> gameList = getListGame();

        repository.saveAll(gameList);

        repository.deleteAll();

        List<Game> result = repository.findAll();

        assertTrue(result.isEmpty());
    }

    private List<Game> getListGame() {
        List<Game> gameList = new ArrayList<>();

        List<Kill> killList = new ArrayList<>();

        killList.add(new Kill("1", "JrMatador", 5));
        killList.add(new Kill("2", "MKiller", 10));
        killList.add(new Kill("3", "SniperCerto", 2));

        Game game = new Game();
        game.setGameNumber(1);
        game.setPlayers(Arrays.asList("JrMatador", "MKiller", "SniperCerto"));
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