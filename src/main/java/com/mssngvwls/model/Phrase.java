package com.mssngvwls.model;

public class Phrase {

    private String fullPhrase;
    private Category category;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((fullPhrase == null) ? 0 : fullPhrase.hashCode());
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
        final Phrase other = (Phrase) obj;
        if (fullPhrase == null) {
            if (other.fullPhrase != null) {
                return false;
            }
        } else if (!fullPhrase.equals(other.fullPhrase)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Phrase [fullPhrase=" + fullPhrase + ", category=" + category + "]";
    }

    public String getFullPhrase() {
        return fullPhrase;
    }

    public void setFullPhrase(final String fullPhrase) {
        this.fullPhrase = fullPhrase;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }
}