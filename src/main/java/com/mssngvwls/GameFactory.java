package com.mssngvwls;

import org.springframework.stereotype.Service;

@Service
public class GameFactory {

    private final GamePhraseSelector phraseSelector;

    public GameFactory(final GamePhraseSelector phraseSelector) {
        this.phraseSelector = phraseSelector;
    }

    public GameState startGame(final int numberOfCategories, final int numberOfPhrasesPerCategory) {
        return new Game(phraseSelector).startGame(numberOfCategories, numberOfPhrasesPerCategory);
    }
}
