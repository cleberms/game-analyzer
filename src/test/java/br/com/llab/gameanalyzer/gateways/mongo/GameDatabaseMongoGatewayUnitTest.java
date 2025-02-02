package br.com.llab.gameanalyzer.gateways.mongo;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToDeleteGameException;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToFindGamesException;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToSaveGameException;
import br.com.llab.gameanalyzer.gateways.exceptions.GameNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class GameDatabaseMongoGatewayUnitTest {

    @Mock
    private GameRopository repository;

    @InjectMocks
    private GameDatabaseMongoGateway gameDatabaseMongoGateway;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldCallGetAllMethodSuccessfully() {

        List<Game> gameList = new ArrayList<>();
        Game game = new Game();
        game.setGameNumber(1);

        gameList.add(game);

        when(repository.findAll()).thenReturn(gameList);

        List<Game> result = gameDatabaseMongoGateway.getAll();

        assertEquals(gameList, result);
        verify(repository, VerificationModeFactory.times(1)).findAll();

    }

    @Test(expected = GameNotFoundException.class)
    public void shouldCallGetAllMethodReturnNotFound() {

        when(repository.findAll()).thenReturn(new ArrayList<>());

        try {
            gameDatabaseMongoGateway.getAll();
        } catch (GameNotFoundException ex) {

            assertEquals("Game not found.", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findAll();

            throw ex;
        }
    }

    @Test (expected = ErrorToFindGamesException.class)
    public void shouldCallGetAllMethodSuccessfullyAndReturnsException() {

        when(repository.findAll()).thenThrow(new RuntimeException());

        try {
            gameDatabaseMongoGateway.getAll();
        } catch (ErrorToFindGamesException ex) {
            assertEquals("Cannot find any game!", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findAll();

            throw ex;
        }
    }

    @Test
    public void shouldCallSaveAllMethodSuccessfully() {

        List<Game> gameList = new ArrayList<>();
        Game game = new Game();
        game.setGameNumber(1);

        gameList.add(game);

        gameDatabaseMongoGateway.saveAll(gameList);

        verify(repository, VerificationModeFactory.times(1)).saveAll(gameList);
        verify(repository, VerificationModeFactory.times(1)).saveAll(any());

    }

    @Test (expected = ErrorToSaveGameException.class)
    public void shouldCallSaveAllMethodSuccessfullyAndReturnsException() {

        List<Game> gameList = new ArrayList<>();
        Game game = new Game();
        game.setGameNumber(1);

        gameList.add(game);

        when(repository.saveAll(gameList)).thenThrow(new RuntimeException());

        try {
            gameDatabaseMongoGateway.saveAll(gameList);
        } catch (ErrorToSaveGameException ex) {
            assertEquals("Cannot save any game!", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).saveAll(any());
            verify(repository, VerificationModeFactory.times(1)).saveAll(gameList);

            throw ex;
        }
    }

    @Test
    public void shouldCallGetByGameNumberMethodSuccessfully() {

        when(repository.findByGameNumber(12)).thenReturn(Optional.of(new Game()));

        gameDatabaseMongoGateway.getByGameNumber(12);

        verify(repository, VerificationModeFactory.times(1)).findByGameNumber(12);
    }

    @Test(expected = GameNotFoundException.class)
    public void shouldCallGetByGameNumberMethodAndReturnGameNotFoundException() {

        when(repository.findByGameNumber(12)).thenReturn(Optional.empty());

        try {
            gameDatabaseMongoGateway.getByGameNumber(12);
        } catch (GameNotFoundException ex) {
            assertEquals("Game not found.", ex.getMessage());

            verify(repository, VerificationModeFactory.times(1)).findByGameNumber(12);

            throw  ex;
        }
    }

    @Test(expected = ErrorToFindGamesException.class)
    public void shouldCallGetByGameNumberMethodAndReturnErrorToFindGamesException() {

        when(repository.findByGameNumber(12)).thenThrow(new RuntimeException());

        try {
            gameDatabaseMongoGateway.getByGameNumber(12);
        } catch (ErrorToFindGamesException ex) {
            assertEquals("Cannot find any game!", ex.getMessage());
            verify(repository, VerificationModeFactory.times(1)).findByGameNumber(12);

            throw ex;
        }
    }

    @Test public void shouldCallDeleteAllMethodSuccessfully() {
        gameDatabaseMongoGateway.deleteAll();

        verify(repository, VerificationModeFactory.times(1)).deleteAll();

    }

    @Test public void shouldCallDeleteAllMethodException() {

        doThrow(new RuntimeException()).when(repository).deleteAll();

        try {
            gameDatabaseMongoGateway.deleteAll();
        } catch (ErrorToDeleteGameException ex) {

            assertEquals("Error to delete game.", ex.getMessage());

            verify(repository, VerificationModeFactory.times(1)).deleteAll();
        }

    }

}