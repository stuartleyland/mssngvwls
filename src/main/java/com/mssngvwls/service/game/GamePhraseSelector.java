package com.mssngvwls.service.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mssngvwls.model.Category;
import com.mssngvwls.model.GamePhrase;
import com.mssngvwls.model.Phrase;
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
        final List<Category> categories = categoryRepository.findAll();

        final List<Category> categoriesMatchingCriteria = categories.stream()
                .filter(category -> category.getPhrases().size() >= numberOfPhrasesPerCategory)
                .collect(Collectors.toList());

        Collections.shuffle(categoriesMatchingCriteria, new Random());

        final Map<Category, List<Phrase>> categoriesToPhrases = categoriesMatchingCriteria.stream()
                .limit(numberOfCategories)
                .collect(Collectors.toMap(category -> category, Category::getPhrases));

        final Map<Category, List<Phrase>> finalCategoriesToPhrases = new HashMap<>();
        for (final Entry<Category, List<Phrase>> entry : categoriesToPhrases.entrySet()) {
            Collections.shuffle(entry.getValue(), new Random());

            finalCategoriesToPhrases.put(entry.getKey(),
                    entry.getValue().stream()
                            .limit(numberOfPhrasesPerCategory)
                            .collect(Collectors.toList()));
        }

        return finalCategoriesToPhrases.values().stream()
                .flatMap(List::stream)
                .map(phrase -> new GamePhraseBuilder()
                        .withCategory(phrase.getCategory().getName())
                        .withFullPhrase(phrase.getFullPhrase())
                        .withPhraseWithoutVowels(phraseFormatter.format(phrase.getFullPhrase()))
                        .build())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}