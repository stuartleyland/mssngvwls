package com.mssngvwls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameTests {

    private static final Category FOOTBALL_TEAMS_CATEGORY = new Category("Football Teams");
    private static final String FOOTBALL_TEAM_1_NAME = "Manchester United";
    private static final Phrase FOOTBALL_TEAM_PHRASE_1 = new Phrase(FOOTBALL_TEAM_1_NAME, "Mnchstr ntd", FOOTBALL_TEAMS_CATEGORY);

    private static final Category GREETINGS_CATEGORY = new Category("Greetings");
    private static final String GREETING_1 = "Yo";
    private static final Phrase GREETINGS_PHRASE_1 = new Phrase(GREETING_1, "Y", GREETINGS_CATEGORY);

    @Mock
    private PhraseSelector phraseSelector;

    @InjectMocks
    private Game game;

    @Test
    public void score_is_initialised_to_zero() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final GameState gameState = game.startGame();

        assertThat(gameState.getScore()).isEqualTo(0);
    }

    @Test
    public void current_category_is_initialised_on_game_start() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final GameState gameState = game.startGame();

        assertThat(gameState.getCurrentCategory().isPresent()).isTrue();
        assertThat(gameState.getCurrentCategory().get()).isEqualTo(FOOTBALL_TEAMS_CATEGORY);
    }

    @Test
    public void current_phrase_is_initialised_on_game_start() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final GameState gameState = game.startGame();

        assertThat(gameState.getCurrentPhrase().isPresent()).isTrue();
        assertThat(gameState.getCurrentPhrase().get()).isEqualTo(FOOTBALL_TEAM_PHRASE_1);
    }

    @Test
    public void previous_guess_correct_is_not_set_on_game_start() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final GameState gameState = game.startGame();

        assertThat(gameState.getPreviousGuessCorrect().isPresent()).isFalse();
    }

    @Test
    public void game_is_not_over_if_game_is_started_with_a_phrase() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final GameState gameState = game.startGame();

        assertThat(gameState.isGameOver()).isFalse();
    }

    @Test
    public void game_is_over_if_no_phrases_are_supplied() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue());

        final GameState gameState = game.startGame();

        assertThat(gameState.isGameOver()).isTrue();
    }

    @Test
    public void score_decreases_if_guess_is_incorrect() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        game.startGame();

        final GameState gameState = game.guessPhrase("incorrect answer");
        assertThat(gameState.getScore()).isEqualTo(-1);
    }

    @Test
    public void previous_guess_correct_is_false_if_guess_is_incorrect() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        game.startGame();

        final GameState gameState = game.guessPhrase("incorrect answer");
        assertThat(gameState.getPreviousGuessCorrect().isPresent()).isTrue();
        assertThat(gameState.getPreviousGuessCorrect().get()).isFalse();
    }

    @Test
    public void previous_guess_correct_is_true_if_guess_is_correct() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        game.startGame();

        final GameState gameState = game.guessPhrase(FOOTBALL_TEAM_1_NAME);
        assertThat(gameState.getPreviousGuessCorrect().isPresent()).isTrue();
        assertThat(gameState.getPreviousGuessCorrect().get()).isTrue();
    }

    @Test
    public void score_increases_if_guess_is_correct() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        game.startGame();

        final GameState gameState = game.guessPhrase(FOOTBALL_TEAM_1_NAME);
        assertThat(gameState.getScore()).isEqualTo(1);
    }

    @Test
    public void game_is_over_after_guessing_one_and_only_phrase() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        game.startGame();

        final GameState gameState = game.guessPhrase(FOOTBALL_TEAM_1_NAME);
        assertThat(gameState.isGameOver()).isTrue();
    }

    @Test
    public void phrase_is_removed_after_it_has_been_selected() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final GameState gameState = game.startGame();

        assertThat(gameState.getPhrases()).doesNotContain(FOOTBALL_TEAM_PHRASE_1);
    }

    @Test
    public void next_phrase_is_selected_after_guessing_phrase() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1, GREETINGS_PHRASE_1));
        game.startGame();
        final GameState gameStateAfterGuess = game.guessPhrase(FOOTBALL_TEAM_1_NAME);

        assertThat(gameStateAfterGuess.getCurrentPhrase().isPresent()).isTrue();
        assertThat(gameStateAfterGuess.getCurrentPhrase().get()).isEqualTo(GREETINGS_PHRASE_1);
    }

    @Test
    public void current_phrase_is_empty_if_game_is_over() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final GameState gameStateAtStart = game.startGame();

        assertThat(gameStateAtStart.getCurrentPhrase().isPresent()).isTrue();
        final GameState gameStateAfterGuess = game.guessPhrase(FOOTBALL_TEAM_1_NAME);
        assertThat(gameStateAfterGuess.getCurrentPhrase().isPresent()).isFalse();
    }

    @Test
    public void current_category_is_empty_if_game_is_over() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final GameState gameStateAtStart = game.startGame();

        assertThat(gameStateAtStart.getCurrentCategory().isPresent()).isTrue();
        final GameState gameStateAfterGuess = game.guessPhrase(FOOTBALL_TEAM_1_NAME);
        assertThat(gameStateAfterGuess.getCurrentCategory().isPresent()).isFalse();
    }

    @Test
    public void score_is_2_if_two_guesses_are_correct() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1, GREETINGS_PHRASE_1));
        game.startGame();

        game.guessPhrase(FOOTBALL_TEAM_1_NAME);
        final GameState gameStateAfterSecondGuess = game.guessPhrase(GREETING_1);
        assertThat(gameStateAfterSecondGuess.getScore()).isEqualTo(2);
    }

    @Test
    public void score_is_0_if_one_guess_is_correct_and_one_is_incorrect() {
        when(phraseSelector.generateCategories()).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1, GREETINGS_PHRASE_1));
        game.startGame();

        game.guessPhrase(FOOTBALL_TEAM_1_NAME);
        final GameState gameStateAfterSecondGuess = game.guessPhrase("incorrect guess");
        assertThat(gameStateAfterSecondGuess.getScore()).isEqualTo(0);
    }

    private Queue<Phrase> createPhraseQueue(final Phrase... phrases) {
        return new LinkedList<Phrase>(Arrays.asList(phrases));
    }
}
