package br.com.llab.gameanalyzer.http.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KillResponseJson {

    @JsonProperty("player_name")
    private String player;

    @JsonProperty("kill_number")
    private Integer killNumber;
}
