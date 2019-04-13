package br.com.llab.gameanalyzer.usecases;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.gateways.GameDatabaseGateway;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToSaveGameException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class GameAnalysisUnitTest {
    @Mock
    private GameLogReader logReader;

    @Mock
    private GameDatabaseGateway gateway;

    @InjectMocks
    private GameAnalysis gameAnalysis;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test public void shouldProcessSuccessfully() {
        when(logReader.parserLog()).thenReturn(Arrays.<Game>asList(new Game()));

        gameAnalysis.process();

        verify(logReader, VerificationModeFactory.times(1)).parserLog();
        verify(gateway, VerificationModeFactory.times(1)).saveAll(any());
    }

    @Test(expected = ErrorToSaveGameException.class)
    public void shouldReturnExceptionFromGateway() {

        doThrow(new ErrorToSaveGameException()).when(gateway).saveAll(any());

        try {
            gameAnalysis.process();
        } catch (ErrorToSaveGameException ex) {

            verify(logReader, VerificationModeFactory.times(1)).parserLog();
            verify(gateway, VerificationModeFactory.times(1)).saveAll(any());

            throw ex;
        }

    }
}