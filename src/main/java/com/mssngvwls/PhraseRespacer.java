package com.mssngvwls;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PhraseRespacer {

    private static final String SPACE = " ";

    private final RandomNumberGenerator randomNumberGenerator;

    public PhraseRespacer(final RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public String respace(final String phrase) {
        String reducedPhrase = phrase.replace(SPACE, "");
        final StringBuilder sb = new StringBuilder();

        while (reducedPhrase.length() > 0) {
            int offset = randomNumberGenerator.between(1, 4);
            if (offset > reducedPhrase.length()) {
                offset = reducedPhrase.length();
            }
            final String group = reducedPhrase.substring(0, offset);
            reducedPhrase = reducedPhrase.substring(offset, reducedPhrase.length());
            sb.append(group);
            if (!StringUtils.isEmpty(reducedPhrase)) {
                sb.append(SPACE);
            }
        }
        return sb.toString();
    }
}