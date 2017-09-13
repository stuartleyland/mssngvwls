package com.mssngvwls;

import org.springframework.stereotype.Service;

@Service
public class GameFactory {

    private final GamePhraseSelector phraseSelector;

    public GameFactory(final GamePhraseSelector phraseSelector) {
        this.phraseSelector = phraseSelector;
    }

    public Game createGame() {
        return new Game(phraseSelector);
    }
}
