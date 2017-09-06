package com.mssngvwls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PhaseRespacerTests {

    @Mock
    private RandomNumberGenerator randomNumberGenerator;

    @InjectMocks
    private PhraseRespacer phraseRespacer;

    @Test
    public void phrase_is_respaced() {
        final String input = "Manchester United";
        when(randomNumberGenerator.between(1, 4)).thenReturn(1);
        final String expectedOutput = "M a n c h e s t e r U n i t e d";
        final String actualOutput = phraseRespacer.respace(input);
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}
