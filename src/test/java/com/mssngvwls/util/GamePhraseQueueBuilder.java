package com.mssngvwls.util;

import java.util.LinkedList;
import java.util.Queue;

import com.mssngvwls.model.GamePhrase;
import com.mssngvwls.model.builder.GamePhraseBuilder;

public class GamePhraseQueueBuilder {

    private final Queue<GamePhrase> gamePhrases = new LinkedList<>();

    public GamePhraseQueueBuilder withGamePhraseFor(final String categoryName, final String fullPhrase, final String phraseWithoutVowels) {
        gamePhrases.add(new GamePhraseBuilder()
                .withCategory(categoryName)
                .withFullPhrase(fullPhrase)
                .withPhraseWithoutVowels(phraseWithoutVowels)
                .build());
        return this;
    }

    public Queue<GamePhrase> build() {
        return gamePhrases;
    }
}
