package com.mssngvwls.service.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
        game.setPhrases(new ArrayList<>(selectedPhrases));

        nextPhrase(game);

        return game;
    }

    private void nextPhrase(final Game game) {
        GamePhrase nextPhrase = null;
        final List<GamePhrase> phrases = game.getPhrases();
        if (!CollectionUtils.isEmpty(phrases)) {
            nextPhrase = phrases.get(0);
            phrases.remove(0);
            game.setPhrases(phrases);
        }

        game.setCurrentPhrase(nextPhrase);
        game.setGameOver(nextPhrase == null);
    }

    public Game guessPhrase(final Long gameId, final String guess) {
        final Game game = gameRepository.findOne(gameId);
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
        game.setPreviousGuessCorrect(true);
    }

    private void decrementScore(final Game game) {
        game.setScore(game.getScore() - 1);
        game.setPreviousGuessCorrect(false);
    }
}
