package br.com.llab.gameanalyzer.usecases;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.gateways.GameDatabaseGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryGameAnaliysis {

    @Autowired
    private GameDatabaseGateway gateway;

    public List<Game> query() {
        return gateway.getAll();
    }

    public Game query(final int gameNumber) {
        return gateway.getByGameNumber(gameNumber);
    }
}
