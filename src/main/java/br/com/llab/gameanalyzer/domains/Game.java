package br.com.llab.gameanalyzer.domains;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Game {

    private int gameNumber;
    private int totalKills;
    private List<String> players;
    private Map<String, Integer> kills;
}
