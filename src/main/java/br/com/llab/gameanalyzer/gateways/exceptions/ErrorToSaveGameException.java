package br.com.llab.gameanalyzer.gateways.exceptions;

public class ErrorToSaveGameException extends RuntimeException {

    private static final String MESSAGE = "Cannot save any game!";

    public ErrorToSaveGameException() {super(MESSAGE);}
}
