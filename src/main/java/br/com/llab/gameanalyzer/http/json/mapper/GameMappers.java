package br.com.llab.gameanalyzer.http.json.mapper;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.http.json.GameResponseJson;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GameMappers {

    List<GameResponseJson> toGameResponseJsonList(final List<Game> gameList);

    GameResponseJson toGameResponseJson(final Game game);

    enum GameMapper {
        ;
        public static final GameMappers MAPPER = Mappers.getMapper(GameMappers.class);
    }
}
