package br.com.llab.gameanalyzer.usecases;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.gateways.GameDatabaseGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameAnalysis {

    @Autowired
    private GameLogReader logReader;

    @Autowired
    private GameDatabaseGateway gateway;

    public void process() {
        List<Game> gameList = logReader.parserLog();

        gateway.saveAll(gameList);
    }
}
