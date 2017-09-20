package com.mssngvwls.util;

import java.util.LinkedList;
import java.util.Queue;

import com.mssngvwls.model.GamePhrase;

public class GamePhraseQueueBuilder {

    private final Queue<GamePhrase> gamePhrases = new LinkedList<>();

    public GamePhraseQueueBuilder withGamePhraseFor(final String categoryName, final String fullPhrase, final String phraseWithoutVowels) {
        gamePhrases.add(new GamePhrase(fullPhrase, phraseWithoutVowels, categoryName));
        return this;
    }

    public Queue<GamePhrase> build() {
        return gamePhrases;
    }
}
