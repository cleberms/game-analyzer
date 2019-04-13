package br.com.llab.gameanalyzer.usecases;

import br.com.llab.gameanalyzer.domains.Game;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class GameLogReader {

    private static final String EMPTY = "";
    private static final int INT_ZERO = 0;
    private static final int INT_ONE = 1;
    private static final String INITIALIZE_GAME_TAG = "InitGame:";
    private static final String USER_INFO_TAG = "ClientUserinfoChanged:";
    private static final String KILL_TAG = "Kill";
    private static final String DOTTED_TAG = "------------------------------------------------------------";
    private static final String SHUTDOWN_GAME_TAG = "ShutdownGame";
    private static final String SPLIT_USER_ID_AND_USER_INFO_REGEX = "n\\\\";
    private static final String MATCH_USER_ADITIONAL_INFO_REGEX = "\\\\t[\\s\\S]*";
    private static final String SPLIT_KILL_INFO_IDS_AND_MESSAGE_REGEX = "\\:\\s";
    private static final String SPLIT_WHITE_SPACE = "\\s";

    @Value("${file-log.path}")
    private String filePath;

    @Value("${file-log.name}")
    private String fileName;

    /**
     * Method make the parser of file log to Game list
     * @return List<Game>
     */
    public List<Game> parserLog() {

        // initialize methods variable
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        Game game = null;
        HashMap<String, String> playerMap = new HashMap<>();
        List<Game> gameList = new ArrayList<>();

        try {

            // Prepare file to be read
            fileReader = new FileReader(filePath.concat(fileName));
            bufferedReader = new BufferedReader(fileReader);

            // read first file line
            String currentLine = bufferedReader.readLine();

            // execute while exist line
            while (null != currentLine) {
                currentLine.trim();

                if(shouldIntializeNewGame(currentLine)){
                    game = initializeNewGame(game, gameList);

                } else  if(shouldShutdownGame(game, currentLine)) {

                    if(game.getKills().size() != playerMap.size()) {

                        for(String value : playerMap.values()) {
                            if(!game.getKills().containsKey(value)) {
                                game.getKills().put(value, INT_ZERO);
                            }
                        }
                    }

                    gameList.add(game);

                    game = null;
                } else if(shouldGetUserinfo(currentLine)) {
                    getUserInfo(game, playerMap, currentLine);
                } else if(shouldGetKillInfo(currentLine)) {
                    getKillInfo(game, playerMap, currentLine);
                }

                currentLine = bufferedReader.readLine();
            }

            return gameList;
        } catch (Exception ex) {
            throw new RuntimeException("Error to complete parser process");
        } finally {
            finalyzeProcess(bufferedReader, fileReader);
        }
    }

    /**
     * Verify if new game starts
     * @param currentLine
     * @return
     */
    private boolean shouldIntializeNewGame(final String currentLine) {
        return currentLine.contains(INITIALIZE_GAME_TAG);
    }

    /**
     * Initialize a new game
     * @param game
     * @param gameList
     * @return
     */
    private Game initializeNewGame(Game game, List<Game> gameList) {

        int currentGameNumber = INT_ONE;

        if(!gameList.isEmpty()) {
            currentGameNumber = gameList.get(gameList.size() - INT_ONE).getGameNumber() + INT_ONE;
        }

        game = new Game();
        game.setGameNumber(currentGameNumber);
        game.setKills(new HashMap<>());
        game.setPlayers(new ArrayList<>());

        return game;
    }

    /**
     * Verify if the line contains a user informations
     * @param currentLine
     * @return
     */
    private boolean shouldGetUserinfo(final String currentLine) {
        return currentLine.contains(USER_INFO_TAG);
    }

    /**
     * Get user informations
     * @param game
     * @param playerMap
     * @param currentLine
     */
    private void getUserInfo(Game game, HashMap<String, String> playerMap, String currentLine) {
        currentLine = getLineValue(currentLine);

        List<String> userDataSplit = Arrays.asList(currentLine.split(SPLIT_USER_ID_AND_USER_INFO_REGEX));

        // separates the player id and player name to add in map
        String playerId = userDataSplit.get(INT_ZERO).trim();
        String playerName = userDataSplit.get(INT_ONE).trim().replaceAll(MATCH_USER_ADITIONAL_INFO_REGEX, EMPTY);

        playerMap.put(playerId, playerName);

        game.setPlayers(new ArrayList<>(playerMap.values()));
    }

    /**
     * Method remove unnecessary line values
     * @param currentLine
     * @return
     */
    private String getLineValue(final String currentLine) {
        return currentLine.replaceAll("(([0-9]*)+\\:+([0-9]*)\\s[a-zA-Z]*+\\:\\s)", EMPTY);
    }

    /**
     * Verify if contains kills informations
     * @param currentLine
     * @return
     */
    private boolean shouldGetKillInfo(final String currentLine) {
        return currentLine.contains(KILL_TAG);
    }

    /**
     * Get and map kill informations
     * @param game
     * @param playerMap
     * @param currentLine
     */
    private void getKillInfo(Game game, HashMap<String, String> playerMap, String currentLine) {
        game.setTotalKills(game.getTotalKills() + INT_ONE);

        currentLine = getLineValue(currentLine);

        List<String> idsAndMessageSplitList = Arrays.asList(currentLine.split(SPLIT_KILL_INFO_IDS_AND_MESSAGE_REGEX));

        List<String> splitIds = Arrays.asList(idsAndMessageSplitList.get(INT_ZERO).trim().split(SPLIT_WHITE_SPACE));

        String idKiller = splitIds.get(INT_ZERO);
        String idKilled = splitIds.get(INT_ONE);

        if(playerMap.containsKey(idKiller) && !idKiller.equalsIgnoreCase(idKilled)) {
            addKillToPlayer(game, playerMap, idKiller);
        } else {
            subtractsPlayerKill(game, playerMap, idKilled);
        }
    }

    /**
     * Add kill to an player
     * @param game
     * @param playerMap
     * @param idKiller
     */
    private void addKillToPlayer(Game game, HashMap<String, String> playerMap, final String idKiller) {
        String playerName = playerMap.get(idKiller);

        if(game.getKills().containsKey(playerName)) {
            Integer kill = game.getKills().get(playerName) + 1;

            game.getKills().put(playerName, kill);
        } else {
            game.getKills().put(playerName, 1);
        }
    }

    /**
     * Substract kill to an player
     * @param game
     * @param playerMap
     * @param idKilled
     */
    private void subtractsPlayerKill(Game game, HashMap<String, String> playerMap, final String idKilled) {
        String playerName = playerMap.get(idKilled);

        if(game.getKills().containsKey(playerName)) {
            Integer kill = game.getKills().get(playerName) - 1;

            game.getKills().put(playerName, kill);
        } else {
            game.getKills().put(playerName, -1);
        }
    }

    /**
     * Verify if need shutdown a game
     * @param game
     * @param currentLine
     * @return
     */
    private boolean shouldShutdownGame(final Game game, final String currentLine) {
        return currentLine.contains(SHUTDOWN_GAME_TAG)
                || (null != game && currentLine.contains(DOTTED_TAG));
    }

    /**
     * Finalyze the process to read a log file
     * @param bufferedReader
     * @param fileReader
     */
    private void finalyzeProcess(BufferedReader bufferedReader, FileReader fileReader) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (fileReader != null) {
                fileReader.close();
            }

        } catch (IOException ex) {

            throw new RuntimeException("Error to finalyze parser process");

        }
    }
}
