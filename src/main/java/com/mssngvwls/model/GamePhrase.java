package com.mssngvwls.model;

public class GamePhrase {

    private final String fullPhrase;
    private final String phraseWithoutVowels;
    private final String category;

    public GamePhrase(final String fullPhrase, final String phraseWithoutVowels, final String category) {
        this.fullPhrase = fullPhrase;
        this.phraseWithoutVowels = phraseWithoutVowels;
        this.category = category;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((category == null) ? 0 : category.hashCode());
        result = (prime * result) + ((fullPhrase == null) ? 0 : fullPhrase.hashCode());
        result = (prime * result) + ((phraseWithoutVowels == null) ? 0 : phraseWithoutVowels.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GamePhrase other = (GamePhrase) obj;
        if (category == null) {
            if (other.category != null) {
                return false;
            }
        } else if (!category.equals(other.category)) {
            return false;
        }
        if (fullPhrase == null) {
            if (other.fullPhrase != null) {
                return false;
            }
        } else if (!fullPhrase.equals(other.fullPhrase)) {
            return false;
        }
        if (phraseWithoutVowels == null) {
            if (other.phraseWithoutVowels != null) {
                return false;
            }
        } else if (!phraseWithoutVowels.equals(other.phraseWithoutVowels)) {
            return false;
        }
        return true;
    }

    public String getFullPhrase() {
        return fullPhrase;
    }

    public String getPhraseWithoutVowels() {
        return phraseWithoutVowels;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "GamePhrase [fullPhrase=" + fullPhrase + ", phraseWithoutVowels=" + phraseWithoutVowels + ", category=" + category + "]";
    }
}