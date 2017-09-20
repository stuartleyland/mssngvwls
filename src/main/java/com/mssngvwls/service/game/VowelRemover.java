package com.mssngvwls.service.game;

import org.springframework.stereotype.Service;

@Service
public class VowelRemover {

    private static final String VOWELS_REGEX = "[aeiouAEIOU]";

    public String removeVowels(final String phrase) {
        return phrase.replaceAll(VOWELS_REGEX, "");
    }

}
