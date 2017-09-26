package com.mssngvwls.service.game;

import java.util.Optional;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.mssngvwls.model.Game;
import com.mssngvwls.model.GamePhrase;
import com.mssngvwls.service.repository.GameRepository;

@Service
public class GameService {

    private final GamePhraseSelector phraseSelector;
    private final GameRepository gameRepository;

    public GameService(final GamePhraseSelector phraseSelector, final GameRepository gameRepository) {
        this.phraseSelector = phraseSelector;
        this.gameRepository = gameRepository;
    }

    public Game startGame(final int numberOfCategories, final int numberOfPhrasesPerCategory) {
        final Game game = new Game();

        final Queue<GamePhrase> selectedPhrases = phraseSelector.generateCategories(numberOfCategories, numberOfPhrasesPerCategory);
        game.setPhrases(selectedPhrases);

        nextPhrase(game);

        return game;
    }

    private void nextPhrase(final Game game) {
        final GamePhrase nextPhrase = game.getPhrases().poll();

        game.setCurrentPhrase(nextPhrase == null ? Optional.empty() : Optional.of(nextPhrase));
        game.setGameOver(nextPhrase == null);
    }

    public Game guessPhrase(final int gameId, final String guess) {
        final Game game = gameRepository.findById(gameId);
        final Optional<GamePhrase> currentPhrase = game.getCurrentPhrase();
        if (currentPhrase.isPresent() && guess.equalsIgnoreCase(currentPhrase.get().getFullPhrase())) {
            incrementScore(game);
        } else {
            decrementScore(game);
        }

        nextPhrase(game);
        return game;
    }

    private void incrementScore(final Game game) {
        game.setScore(game.getScore() + 1);
        game.setPreviousGuessCorrect(Optional.of(true));
    }

    private void decrementScore(final Game game) {
        game.setScore(game.getScore() - 1);
        game.setPreviousGuessCorrect(Optional.of(false));
    }
}
