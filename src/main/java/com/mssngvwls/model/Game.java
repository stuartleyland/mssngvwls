package com.mssngvwls.model;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Game {

    private int id = 0;
    private Queue<GamePhrase> phrases = new LinkedList<>();
    private int score = 0;
    private Optional<GamePhrase> currentPhrase = Optional.empty();
    private Optional<Boolean> previousGuessCorrect = Optional.empty();
    private boolean gameOver = false;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((currentPhrase == null) ? 0 : currentPhrase.hashCode());
        result = (prime * result) + (gameOver ? 1231 : 1237);
        result = (prime * result) + ((phrases == null) ? 0 : phrases.hashCode());
        result = (prime * result) + ((previousGuessCorrect == null) ? 0 : previousGuessCorrect.hashCode());
        result = (prime * result) + score;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Game other = (Game) obj;
        if (currentPhrase == null) {
            if (other.currentPhrase != null) {
                return false;
            }
        } else if (!currentPhrase.equals(other.currentPhrase)) {
            return false;
        }
        if (gameOver != other.gameOver) {
            return false;
        }
        if (phrases == null) {
            if (other.phrases != null) {
                return false;
            }
        } else if (!phrases.equals(other.phrases)) {
            return false;
        }
        if (previousGuessCorrect == null) {
            if (other.previousGuessCorrect != null) {
                return false;
            }
        } else if (!previousGuessCorrect.equals(other.previousGuessCorrect)) {
            return false;
        }
        if (score != other.score) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public Queue<GamePhrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(final Queue<GamePhrase> phrases) {
        this.phrases = phrases;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
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

    public void setPreviousGuessCorrect(final Optional<Boolean> previousGuessCorrect) {
        this.previousGuessCorrect = previousGuessCorrect;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Optional<String> getCurrentCategory() {
        return currentPhrase.isPresent() ? Optional.of(currentPhrase.get().getCategory()) : Optional.empty();
    }
}