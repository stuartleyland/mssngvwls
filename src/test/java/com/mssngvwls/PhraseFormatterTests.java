package com.mssngvwls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PhraseFormatterTests {

    @Mock
    private RandomNumberGenerator randomNumberGenerator;

    private PhraseRespacer phraseRespacer;
    private VowelRemover vowelRemover;
    private PhraseFormatter phraseFormatter;

    @Before
    public void setup() {
        phraseRespacer = new PhraseRespacer(randomNumberGenerator);
        vowelRemover = new VowelRemover();
        phraseFormatter = new PhraseFormatter(vowelRemover, phraseRespacer);
    }

    @Test
    public void phrase_is_formatted() {
        final String input = "Manchester United";
        when(randomNumberGenerator.between(1, 4)).thenReturn(3, 2, 4, 1);
        final String expectedOutput = "MNC HS TRNT D";
        final String actualOutput = phraseFormatter.format(input);
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}
