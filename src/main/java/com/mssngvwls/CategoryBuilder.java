package com.mssngvwls;

import java.util.ArrayList;
import java.util.List;

public class CategoryBuilder {

    private final Category category = new Category();
    private final List<Phrase> phrases = new ArrayList<>();

    public CategoryBuilder withCategoryName(final String categoryName) {
        category.setName(categoryName);
        return this;
    }

    public CategoryBuilder withPhrase(final String fullPhrase) {
        final Phrase phrase = new Phrase();
        phrase.setFullPhrase(fullPhrase);
        phrase.setCategory(category);
        this.phrases.add(phrase);
        return this;
    }

    public CategoryBuilder withPhrases(final List<String> fullPhrases) {
        fullPhrases.forEach(this::withPhrase);
        return this;
    }

    public Category build() {
        category.setPhrases(phrases);
        return category;
    }
}