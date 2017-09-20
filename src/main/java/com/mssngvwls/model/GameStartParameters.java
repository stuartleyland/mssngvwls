package com.mssngvwls.model;

public class GameStartParameters {

    private int numberOfCategories;
    private int numberOfPhrasesPerCategory;

    public int getNumberOfCategories() {
        return numberOfCategories;
    }

    public void setNumberOfCategories(final int numberOfCategories) {
        this.numberOfCategories = numberOfCategories;
    }

    public int getNumberOfPhrasesPerCategory() {
        return numberOfPhrasesPerCategory;
    }

    public void setNumberOfPhrasesPerCategory(final int numberOfPhrasesPerCategory) {
        this.numberOfPhrasesPerCategory = numberOfPhrasesPerCategory;
    }

}
