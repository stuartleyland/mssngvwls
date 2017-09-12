package com.mssngvwls;

import java.util.Optional;
import java.util.Queue;

public class GameState {

    private final Queue<GamePhrase> phrases;
    private int score;
    private Optional<GamePhrase> currentPhrase;
    private Optional<Boolean> previousGuessCorrect;
    private boolean gameOver;

    public GameState(final Queue<GamePhrase> phrases) {
        this.phrases = phrases;
        this.currentPhrase = Optional.empty();
        this.score = 0;
        this.previousGuessCorrect = Optional.empty();
        this.gameOver = false;
    }

    public void incrementScore() {
        this.score++;
        this.previousGuessCorrect = Optional.of(true);
    }

    public void decrementScore() {
        this.score--;
        this.previousGuessCorrect = Optional.of(false);
    }

    public Queue<GamePhrase> getPhrases() {
        return phrases;
    }

    public int getScore() {
        return score;
    }

    public Optional<String> getCurrentCategory() {
        return currentPhrase.isPresent() ? Optional.of(currentPhrase.get().getCategory()) : Optional.empty();
    }

    public Optional<GamePhrase> getCurrentPhrase() {
        return currentPhrase;
    }

    public void setCurrentPhrase(final Optional<GamePhrase> currentPhrase) {
        this.currentPhrase = currentPhrase;
    }

    public Optional<Boolean> getPreviousGuessCorrect() {
        return previousGuessCorrect;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
    }
}
