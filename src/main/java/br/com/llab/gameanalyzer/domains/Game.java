package br.com.llab.gameanalyzer.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "jogo")
public class Game {

    @Id
    private String uid;

    @Field("numero_jogo")
    private int gameNumber;

    @Field("total_mortes")
    private int totalKills;

    @Field("jogadores")
    private List<String> players;

    @Field("mortes")
    private List<Kill> kills;
}
