package br.com.llab.gameanalyzer.usecases;

import br.com.llab.gameanalyzer.gateways.GameDatabaseGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteGameAnalysis {

    @Autowired
    private GameDatabaseGateway gateway;

    public void deleteAll() {
        gateway.deleteAll();
    }
}
