package br.com.llab.gameanalyzer.usecases;

import br.com.llab.gameanalyzer.gateways.GameDatabaseGateway;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToDeleteGameException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DeleteGameAnalysisUnitTest {
    @Mock
    private GameDatabaseGateway gateway;

    @InjectMocks
    private DeleteGameAnalysis deleteGameAnalysis;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldDeleteAllSucessfully() throws Exception {
        deleteGameAnalysis.deleteAll();

        verify(gateway, VerificationModeFactory.times(1)).deleteAll();
    }

    @Test (expected = ErrorToDeleteGameException.class)
    public void shouldDeleteAllReturnException() throws Exception {
        doThrow(new ErrorToDeleteGameException()).when(gateway).deleteAll();

        try {
            deleteGameAnalysis.deleteAll();
        } catch (ErrorToDeleteGameException ex) {

            assertEquals("Error to delete game.", ex.getMessage());

            verify(gateway, VerificationModeFactory.times(1)).deleteAll();

            throw ex;
        }
    }
}
