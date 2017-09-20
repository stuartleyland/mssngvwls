package com.mssngvwls.service.game;

import org.springframework.stereotype.Service;

@Service
public class PhraseFormatter {

    private final VowelRemover vowelRemover;
    private final PhraseRespacer respacer;

    public PhraseFormatter(final VowelRemover vowelRemover, final PhraseRespacer phraseRespacer) {
        this.vowelRemover = vowelRemover;
        this.respacer = phraseRespacer;
    }

    public String format(final String phrase) {
        final String uppercasedPhrase = phrase.toUpperCase();
        final String phraseWithVowelsRemoved = vowelRemover.removeVowels(uppercasedPhrase);
        return respacer.respace(phraseWithVowelsRemoved);
    }
}
