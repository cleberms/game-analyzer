package br.com.llab.gameanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameAnalyzerApplication.class, args);
	}

	@Bean(initMethod="init")
	public InitializationTask exBean() {
		return new InitializationTask();
	}

}
