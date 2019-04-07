package br.com.llab.gameanalyzer.domains;

import lombok.Data;

import java.util.List;

@Data
public class Game {

    private int gameNumber;
    private String totalKills;
    private List<String> players;
    private List<Kill>kills;
}
