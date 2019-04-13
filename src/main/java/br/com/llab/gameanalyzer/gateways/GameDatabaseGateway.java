package br.com.llab.gameanalyzer.gateways;

import br.com.llab.gameanalyzer.domains.Game;

import java.util.List;

public interface GameDatabaseGateway {

    List<Game> getAll();

    Game getByGameNumber(final int gameNumber);

    void saveAll(final List<Game> gameList);
}
