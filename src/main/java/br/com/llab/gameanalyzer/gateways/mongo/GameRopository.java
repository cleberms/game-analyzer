package br.com.llab.gameanalyzer.gateways.mongo;

import br.com.llab.gameanalyzer.domains.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameRopository extends MongoRepository<Game, String> {

    Optional<Game> findByGameNumber(final int gameNumber);
}
