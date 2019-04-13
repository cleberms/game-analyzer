package br.com.llab.gameanalyzer.gateways.mongo;

import br.com.llab.gameanalyzer.domains.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRopository extends MongoRepository<Game, String> {}
