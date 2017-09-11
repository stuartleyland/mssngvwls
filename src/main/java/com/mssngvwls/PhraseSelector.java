package com.mssngvwls;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class PhraseSelector {

    private final PhraseFormatter phraseFormatter;

    public PhraseSelector(final PhraseFormatter phraseFormatter) {
        this.phraseFormatter = phraseFormatter;
    }

    public Queue<Phrase> generateCategories() {
        return new LinkedList<>();
    }

}
