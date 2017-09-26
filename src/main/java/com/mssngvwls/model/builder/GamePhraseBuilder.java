package com.mssngvwls.model.builder;

import com.mssngvwls.model.GamePhrase;

public class GamePhraseBuilder {

    private final GamePhrase gamePhrase = new GamePhrase();

    public GamePhraseBuilder withFullPhrase(final String fullPhrase) {
        gamePhrase.setFullPhrase(fullPhrase);
        return this;
    }

    public GamePhraseBuilder withPhraseWithoutVowels(final String phraseWithoutVowels) {
        gamePhrase.setPhraseWithoutVowels(phraseWithoutVowels);
        return this;
    }

    public GamePhraseBuilder withCategory(final String category) {
        gamePhrase.setCategory(category);
        return this;
    }

    public GamePhrase build() {
        return gamePhrase;
    }
}
