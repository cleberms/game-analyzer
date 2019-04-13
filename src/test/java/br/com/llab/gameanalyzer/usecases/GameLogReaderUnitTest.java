package br.com.llab.gameanalyzer.usecases;

import br.com.llab.gameanalyzer.domains.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameLogReaderUnitTest {

    private static final String TEST_FILE_PATH = "src/test/log/";
    private static final String INIT_GAME_LOG = "initGame.log";
    private static final String KILL_GAME_LOG = "killGame.log";
    private static final String USER_INFO_GAME_LOG = "userInfoGame.log";
    private static final String GAME_LOG = "game.log";

    @InjectMocks
    GameLogReader gameLogReader;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(gameLogReader, "filePath", TEST_FILE_PATH );
    }

    @Test
    public void shouldParserInitGameSuccessfully() {

        ReflectionTestUtils.setField(gameLogReader, "fileName", INIT_GAME_LOG );

        List<Game> gameList = gameLogReader.parserLog();

        assertEquals(3, gameList.size());
        assertEquals(1, gameList.get(0).getGameNumber());
        assertEquals(2, gameList.get(1).getGameNumber());
        assertEquals(3, gameList.get(2).getGameNumber());
    }

    @Test
    public void shouldParserUserInfoGameSuccessfully() {

        ReflectionTestUtils.setField(gameLogReader, "fileName", USER_INFO_GAME_LOG );

        List<Game> gameList = gameLogReader.parserLog();

        assertEquals(1, gameList.size());
        assertEquals(4, gameList.get(0).getPlayers().size());
        assertEquals("Isgalamido", gameList.get(0).getPlayers().get(0));
        assertEquals("Mocinha", gameList.get(0).getPlayers().get(1));
        assertEquals("Zeh", gameList.get(0).getPlayers().get(2));
        assertEquals("Assasinu Credi", gameList.get(0).getPlayers().get(3));
    }

    @Test
    public void shouldParserKillGameSuccessfully() {

        ReflectionTestUtils.setField(gameLogReader, "fileName", KILL_GAME_LOG );

        List<Game> gameList = gameLogReader.parserLog();

        assertEquals(1, gameList.size());
        assertEquals(9, gameList.get(0).getTotalKills());

        assertEquals(2, gameList.get(0).getKills().size());

        assertEquals("Isgalamido", gameList.get(0).getKills().get(0).getPlayer());
        assertEquals("2", gameList.get(0).getKills().get(0).getIdPlayer());
        assertEquals(Integer.valueOf(1), gameList.get(0).getKills().get(0).getKillNumber());

        assertEquals("Mocinha", gameList.get(0).getKills().get(1).getPlayer());
        assertEquals("3", gameList.get(0).getKills().get(1).getIdPlayer());
        assertEquals(Integer.valueOf(2), gameList.get(0).getKills().get(1).getKillNumber());
    }

    @Test
    public void shouldParserGameLogSuccessfully() {

        ReflectionTestUtils.setField(gameLogReader, "fileName", GAME_LOG );

        List<Game> gameList = gameLogReader.parserLog();

        assertEquals(3, gameList.size());

        assertEquals(1, gameList.get(0).getGameNumber());
        assertEquals(1, gameList.get(0).getPlayers().size());
        assertEquals("Isgalamido", gameList.get(0).getPlayers().get(0));
        assertEquals(0, gameList.get(0).getTotalKills());

        assertEquals(1, gameList.get(0).getKills().size());

        assertEquals("Isgalamido", gameList.get(0).getKills().get(0).getPlayer());
        assertEquals("2", gameList.get(0).getKills().get(0).getIdPlayer());
        assertEquals(Integer.valueOf(0), gameList.get(0).getKills().get(0).getKillNumber());

        assertEquals(2, gameList.get(1).getGameNumber());
        assertEquals(2, gameList.get(1).getPlayers().size());
        assertEquals("Isgalamido", gameList.get(1).getPlayers().get(0));
        assertEquals("Mocinha", gameList.get(1).getPlayers().get(1));
        assertEquals(11, gameList.get(1).getTotalKills());

        assertEquals(2, gameList.get(1).getKills().size());

        assertEquals("Isgalamido", gameList.get(1).getKills().get(0).getPlayer());
        assertEquals(Integer.valueOf(-9), gameList.get(1).getKills().get(0).getKillNumber());
        assertEquals("2", gameList.get(1).getKills().get(0).getIdPlayer());

        assertEquals("Mocinha", gameList.get(1).getKills().get(1).getPlayer());
        assertEquals(Integer.valueOf(0), gameList.get(1).getKills().get(1).getKillNumber());
        assertEquals("3", gameList.get(1).getKills().get(1).getIdPlayer());

        assertEquals(3, gameList.get(2).getGameNumber());
        assertEquals(3, gameList.get(2).getPlayers().size());
        assertEquals("Dono da Bola", gameList.get(2).getPlayers().get(0));
        assertEquals("Isgalamido", gameList.get(2).getPlayers().get(1));
        assertEquals("Zeh", gameList.get(2).getPlayers().get(2));
        assertEquals(4, gameList.get(2).getTotalKills());

        assertEquals(3, gameList.get(2).getKills().size());

        assertEquals(Integer.valueOf(1), gameList.get(2).getKills().get(0).getKillNumber());
        assertEquals("Isgalamido", gameList.get(2).getKills().get(0).getPlayer());
        assertEquals("3", gameList.get(2).getKills().get(0).getIdPlayer());

        assertEquals(Integer.valueOf(-2), gameList.get(2).getKills().get(1).getKillNumber());
        assertEquals("Zeh", gameList.get(2).getKills().get(1).getPlayer());
        assertEquals("4", gameList.get(2).getKills().get(1).getIdPlayer());

        assertEquals(Integer.valueOf(-1), gameList.get(2).getKills().get(2).getKillNumber());
        assertEquals("Dono da Bola", gameList.get(2).getKills().get(2).getPlayer());
        assertEquals("2", gameList.get(2).getKills().get(2).getIdPlayer());
    }

    @Test(expected = RuntimeException.class)
    public void shouldReturnExceptionWhenParserGame() {

        ReflectionTestUtils.setField(gameLogReader, "fileName", "unexists" );

        try {
            gameLogReader.parserLog();
        } catch (RuntimeException ex) {
            assertEquals("Error to complete parser process", ex.getMessage());
            throw ex;
        }
    }
}