package com.mssngvwls;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VowelRemoverTests {

    @Autowired
    private VowelRemover vowelRemover;

    @Test
    public void lowercased_vowels_are_removed() {
        final String wordWithVowels = "united";
        final String expectedWordWithoutVowels = "ntd";
        final String actualWordWithoutVowels = vowelRemover.removeVowels(wordWithVowels);
        assertThat(actualWordWithoutVowels).isEqualTo(expectedWordWithoutVowels);
    }

    @Test
    public void uppercased_vowels_are_removed() {
        final String wordWithVowels = "United";
        final String expectedWordWithoutVowels = "ntd";
        final String actualWordWithoutVowels = vowelRemover.removeVowels(wordWithVowels);
        assertThat(actualWordWithoutVowels).isEqualTo(expectedWordWithoutVowels);
    }

    @Test
    public void empty_string_is_returned_if_phrase_is_all_vowels() {
        final String input = "AEIOUaeiou";
        final String expectedOutput = "";
        final String actualOutput = vowelRemover.removeVowels(input);
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    public void vowels_are_removed_from_multiple_words() {
        final String input = "Manchester United";
        final String expectedOutput = "Mnchstr ntd";
        final String actualOutput = vowelRemover.removeVowels(input);
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}
