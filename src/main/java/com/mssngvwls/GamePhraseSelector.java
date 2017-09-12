package com.mssngvwls;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class GamePhraseSelector {

    private final PhraseRepository phraseRepository;
    private final PhraseFormatter phraseFormatter;

    public GamePhraseSelector(final PhraseRepository phraseRepository, final PhraseFormatter phraseFormatter) {
        this.phraseRepository = phraseRepository;
        this.phraseFormatter = phraseFormatter;
    }

    public Queue<GamePhrase> generateCategories() {
        final List<Phrase> phrases = phraseRepository.getPhrases();
        Collections.shuffle(phrases);
        return phrases.stream()
                .limit(10)
                .map(phrase -> new GamePhrase(phrase.getFullPhrase(), phraseFormatter.format(phrase.getFullPhrase()), phrase.getCategory().getName()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
