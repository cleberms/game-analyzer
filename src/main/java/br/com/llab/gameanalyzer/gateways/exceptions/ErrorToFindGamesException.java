package br.com.llab.gameanalyzer.gateways.exceptions;

public class ErrorToFindGamesException extends RuntimeException {

    private static final String MESSAGE = "Cannot find any game!";

    public ErrorToFindGamesException() {super(MESSAGE);}
}
