package com.mssngvwls;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class GamePhraseSelector {

    private final CategoryRepository categoryRepository;
    private final PhraseFormatter phraseFormatter;

    public GamePhraseSelector(final CategoryRepository categoryRepository, final PhraseFormatter phraseFormatter) {
        this.categoryRepository = categoryRepository;
        this.phraseFormatter = phraseFormatter;
    }

    public Queue<GamePhrase> generateCategories(final int numberOfCategories, final int numberOfPhrasesPerCategory) {
        final List<Category> categories = categoryRepository.getAllCategories();

        return categories.stream()
                .filter(category -> category.getPhrases().size() >= numberOfPhrasesPerCategory)
                .limit(numberOfCategories)
                .map(Category::getPhrases)
                .flatMap(phrases -> phrases.stream().limit(numberOfPhrasesPerCategory))
                .map(phrase -> new GamePhrase(phrase.getFullPhrase(), phraseFormatter.format(phrase.getFullPhrase()), phrase.getCategory().getName()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
