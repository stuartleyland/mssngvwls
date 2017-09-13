package com.mssngvwls;

import java.util.List;

public class Category {

    private final String name;
    private final List<Phrase> phrases;

    public Category(final String name, final List<Phrase> phrases) {
        this.name = name;
        this.phrases = phrases;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        result = (prime * result) + ((phrases == null) ? 0 : phrases.hashCode());
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
        final Category other = (Category) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (phrases == null) {
            if (other.phrases != null) {
                return false;
            }
        } else if (!phrases.equals(other.phrases)) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public List<Phrase> getPhrases() {
        return phrases;
    }
}
