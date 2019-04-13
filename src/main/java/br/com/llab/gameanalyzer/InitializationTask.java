package br.com.llab.gameanalyzer;

import br.com.llab.gameanalyzer.usecases.DeleteGameAnalysis;
import br.com.llab.gameanalyzer.usecases.GameAnalysis;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitializationTask  implements InitializingBean {

    @Autowired
    private GameAnalysis gameAnalysis;

    @Autowired
    private DeleteGameAnalysis deleteGameAnalysis;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean");
    }

    public void init() {
        deleteGameAnalysis.deleteAll();
        gameAnalysis.process();
    }
}
