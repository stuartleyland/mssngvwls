package com.mssngvwls.service.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mssngvwls.model.Category;
import com.mssngvwls.model.GamePhrase;
import com.mssngvwls.model.builder.GamePhraseBuilder;
import com.mssngvwls.service.repository.CategoryRepository;

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
                .map(phrase -> new GamePhraseBuilder()
                        .withCategory(phrase.getCategory().getName())
                        .withFullPhrase(phrase.getFullPhrase())
                        .withPhraseWithoutVowels(phraseFormatter.format(phrase.getFullPhrase()))
                        .build())
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
