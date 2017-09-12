package com.mssngvwls;

import java.util.Optional;

public class Game {

    private final GamePhraseSelector phraseSelector;

    private GameState gameState = null;

    public Game(final GamePhraseSelector phraseSelector) {
        this.phraseSelector = phraseSelector;
    }

    public GameState startGame() {
        gameState = new GameState(phraseSelector.generateCategories());
        nextPhrase();

        return gameState;
    }

    private void nextPhrase() {
        final GamePhrase nextPhrase = gameState.getPhrases().poll();

        gameState.setCurrentPhrase(nextPhrase == null ? Optional.empty() : Optional.of(nextPhrase));
        gameState.setGameOver(nextPhrase == null);
    }

    public GameState guessPhrase(final String guess) {
        final Optional<GamePhrase> currentPhrase = gameState.getCurrentPhrase();
        if (currentPhrase.isPresent() && guess.equalsIgnoreCase(currentPhrase.get().getFullPhrase())) {
            gameState.incrementScore();
        } else {
            gameState.decrementScore();
        }

        nextPhrase();
        return gameState;
    }
}
