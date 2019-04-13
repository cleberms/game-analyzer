package br.com.llab.gameanalyzer.http;

import br.com.llab.gameanalyzer.domains.Game;
import br.com.llab.gameanalyzer.gateways.exceptions.GameNotFoundException;
import br.com.llab.gameanalyzer.http.json.ErrorJson;
import br.com.llab.gameanalyzer.http.json.GameResponseJson;
import br.com.llab.gameanalyzer.http.json.mapper.GameMappers.GameMapper;
import br.com.llab.gameanalyzer.usecases.QueryGameAnalysis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@Api(tags = "Game analysis", produces = MediaType.APPLICATION_JSON_VALUE)
public class GameAnalysisController {

    @Autowired
    private QueryGameAnalysis queryGameAnalysis;

    @ApiOperation(value = "Resource to Get game analisys")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok")
    })
    @GetMapping("game/analysis")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GameResponseJson>> queryGameAnalysis() {

        try {
            List<Game> gameList = queryGameAnalysis.query();

            return new ResponseEntity(biuldListGameResponseJson(gameList), HttpStatus.OK);
        } catch (GameNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity(new ErrorJson(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Resource to Get game analisys by number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ok"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("game/analysis/{gameNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<GameResponseJson>> queryGameAnalysis(@PathVariable("gameNumber") final int gameNumber) {

        try {
            Game game = queryGameAnalysis.query(gameNumber);

            return new ResponseEntity(biuldGameResponseJson(game), HttpStatus.OK);
        } catch (GameNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex){
            return new ResponseEntity(new ErrorJson(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<GameResponseJson> biuldListGameResponseJson(final List<Game> gameList) {
        return GameMapper.MAPPER.toGameResponseJsonList(gameList);
    }

    private GameResponseJson biuldGameResponseJson(final Game game) {
        return GameMapper.MAPPER.toGameResponseJson(game);
    }
}
