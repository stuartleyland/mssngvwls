package com.mssngvwls.service.game.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mssngvwls.service.game.PhraseRespacer;
import com.mssngvwls.service.util.RandomNumberGenerator;

@RunWith(MockitoJUnitRunner.class)
public class PhraseRespacerTests {

    @Mock
    private RandomNumberGenerator randomNumberGenerator;

    @InjectMocks
    private PhraseRespacer phraseRespacer;

    @Test
    public void phrase_is_respaced_with_equal_spacings() {
        final String input = "Manchester United";
        when(randomNumberGenerator.between(1, 4)).thenReturn(1);
        final String expectedOutput = "M a n c h e s t e r U n i t e d";
        final String actualOutput = phraseRespacer.respace(input);
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    public void phrase_is_respaced_with_different_spacings() {
        final String input = "Manchester United";
        when(randomNumberGenerator.between(1, 4)).thenReturn(3, 2, 4, 3, 1, 3);
        final String expectedOutput = "Man ch este rUn i ted";
        final String actualOutput = phraseRespacer.respace(input);
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }
}
