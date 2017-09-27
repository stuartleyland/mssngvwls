package com.mssngvwls.service.controller.model;

public class GuessPhraseParameters {

    private Long gameId;
    private String guess;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(final Long gameId) {
        this.gameId = gameId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(final String guess) {
        this.guess = guess;
    }

}
