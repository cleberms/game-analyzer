package br.com.llab.gameanalyzer.gateways.mongo;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.gateways.GameDatabaseGateway;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToDeleteGameException;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToFindGamesException;
import br.com.llab.gameanalyzer.gateways.exceptions.ErrorToSaveGameException;
import br.com.llab.gameanalyzer.gateways.exceptions.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    @Override public Game getByGameNumber(final int gameNumber) {

        try{
            Optional<Game> game = repository.findByGameNumber(gameNumber);

            if(game.isPresent()) {
                return game.get();
            }
        } catch (Exception ex) {
            throw new ErrorToFindGamesException();
        }

        throw new GameNotFoundException();
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

    @Override
    public void deleteAll() {
        try {
            repository.deleteAll();
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace());

            throw new ErrorToDeleteGameException();
        }
    }
}
