package br.com.llab.gameanalyzer.http.json;

import br.com.llab.gameanalyzer.domains.Kill;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GameResponseJson {

    @JsonProperty("game_number")
    private int gameNumber;

    @JsonProperty("total_kills")
    private int totalKills;

    @JsonProperty("players")
    private List<String> players;

    @JsonProperty("kills")
    private List<KillResponseJson> kills;
}
