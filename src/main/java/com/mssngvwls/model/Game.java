package com.mssngvwls.model;

import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int score;
    private Boolean previousGuessCorrect;
    private boolean gameOver;

    @OneToMany(fetch = FetchType.EAGER)
    private List<GamePhrase> phrases;

    @OneToOne(fetch = FetchType.EAGER)
    private GamePhrase currentPhrase;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
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
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<GamePhrase> getPhrases() {
        return phrases;
    }

    public void setPhrases(final List<GamePhrase> phrases) {
        this.phrases = phrases;
    }

    public int getScore() {
        return score;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public Optional<GamePhrase> getCurrentPhrase() {
        return Optional.ofNullable(currentPhrase);
    }

    public void setCurrentPhrase(final GamePhrase currentPhrase) {
        this.currentPhrase = currentPhrase;
    }

    public Optional<Boolean> getPreviousGuessCorrect() {
        return Optional.ofNullable(previousGuessCorrect);
    }

    public void setPreviousGuessCorrect(final Boolean previousGuessCorrect) {
        this.previousGuessCorrect = previousGuessCorrect;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Optional<String> getCurrentCategory() {
        return getCurrentPhrase().isPresent() ? Optional.of(getCurrentPhrase().get().getCategory()) : Optional.empty();
    }
}