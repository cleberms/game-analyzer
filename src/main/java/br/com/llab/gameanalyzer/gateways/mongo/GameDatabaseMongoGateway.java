package br.com.llab.gameanalyzer.gateways.mongo;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.gateways.GameDatabaseGateway;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToFindGamesException;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToSaveGameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameDatabaseMongoGateway implements GameDatabaseGateway {

    @Autowired
    private GameRopository repository;

    @Override
    public List<Game> getAll() {

        try {
            return repository.findAll();
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());

            throw new ErrorToFindGamesException();
        }
    }

    @Override
    public void saveAll(final List<Game> gameList) {
        try {
            repository.saveAll(gameList);
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());

            throw new ErrorToSaveGameException();
        }
    }
}
