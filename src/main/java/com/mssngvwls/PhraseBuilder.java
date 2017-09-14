package com.mssngvwls;

public class PhraseBuilder {

    private final Phrase phrase = new Phrase();

    public PhraseBuilder withFullPhrase(final String fullPhrase) {
        phrase.setFullPhrase(fullPhrase);
        return this;
    }

    public PhraseBuilder withCategory(final Category category) {
        phrase.setCategory(category);
        return this;
    }

    public Phrase build() {
        return phrase;
    }
}
