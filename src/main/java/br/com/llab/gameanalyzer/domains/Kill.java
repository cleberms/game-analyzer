package br.com.llab.gameanalyzer.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "mortes")
@AllArgsConstructor
@NoArgsConstructor
public class Kill {

    @Field("codigo_jogador_partida")
    private String idPlayer;

    @Field("nome_jogador")
    private String player;

    @Field("numero_mortes")
    private Integer killNumber;
}
