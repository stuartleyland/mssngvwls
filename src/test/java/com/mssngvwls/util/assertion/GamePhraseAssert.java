package com.mssngvwls.util.assertion;

import java.util.List;

import org.assertj.core.api.AbstractAssert;

import com.mssngvwls.model.GamePhrase;

public class GamePhraseAssert extends AbstractAssert<GamePhraseAssert, List<GamePhrase>> {

    public GamePhraseAssert(final List<GamePhrase> actual) {
        super(actual, GamePhraseAssert.class);
    }

    public static GamePhraseAssert assertThat(final List<GamePhrase> actual) {
        return new GamePhraseAssert(actual);
    }

    public GamePhraseAssert doesNotHavePhrase(final String phrase) {
        final long nummberOfMatchingPhrases = actual.stream()
                .filter(gamePhrase -> !gamePhrase.getFullPhrase().equals(phrase))
                .count();

        if (nummberOfMatchingPhrases != 0) {
            failWithMessage("GamePhrases contained %s but it shouldn't", phrase);
        }
        return this;
    }
}
