package com.mssngvwls.service.game.test;

import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAMS_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_1_NAME;
import static com.mssngvwls.util.TestUtils.GREETINGS_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.GREETING_1;
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

import com.mssngvwls.model.Game;
import com.mssngvwls.model.GamePhrase;
import com.mssngvwls.service.game.GamePhraseSelector;
import com.mssngvwls.service.game.GameService;
import com.mssngvwls.service.repository.GameRepository;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTests {

    private static final String INCORRECT_ANSWER = "incorrect answer";
    private static final GamePhrase FOOTBALL_TEAM_PHRASE_1 = new GamePhrase(FOOTBALL_TEAM_1_NAME, "Mnchstr ntd", FOOTBALL_TEAMS_CATEGORY_NAME);
    private static final GamePhrase GREETINGS_PHRASE_1 = new GamePhrase(GREETING_1, "Y", GREETINGS_CATEGORY_NAME);

    @Mock
    private GamePhraseSelector phraseSelector;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    @Test
    public void score_is_initialised_to_zero() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.getScore()).isEqualTo(0);
    }

    @Test
    public void current_category_is_initialised_on_game_start() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.getCurrentCategory().isPresent()).isTrue();
        assertThat(gameState.getCurrentCategory().get()).isEqualTo(FOOTBALL_TEAMS_CATEGORY_NAME);
    }

    @Test
    public void current_phrase_is_initialised_on_game_start() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.getCurrentPhrase().isPresent()).isTrue();
        assertThat(gameState.getCurrentPhrase().get()).isEqualTo(FOOTBALL_TEAM_PHRASE_1);
    }

    @Test
    public void previous_guess_correct_is_not_set_on_game_start() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.getPreviousGuessCorrect().isPresent()).isFalse();
    }

    @Test
    public void game_is_not_over_if_game_is_started_with_a_phrase() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.isGameOver()).isFalse();
    }

    @Test
    public void game_is_over_if_no_phrases_are_supplied() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue());

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.isGameOver()).isTrue();
    }

    @Test
    public void score_decreases_if_guess_is_incorrect() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final Game gameAtStart = gameService.startGame(1, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        final Game gameAfterGuess = gameService.guessPhrase(id, INCORRECT_ANSWER);
        assertThat(gameAfterGuess.getScore()).isEqualTo(-1);
    }

    @Test
    public void previous_guess_correct_is_false_if_guess_is_incorrect() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final Game gameAtStart = gameService.startGame(1, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        final Game gameAfterGuess = gameService.guessPhrase(id, INCORRECT_ANSWER);
        assertThat(gameAfterGuess.getPreviousGuessCorrect().isPresent()).isTrue();
        assertThat(gameAfterGuess.getPreviousGuessCorrect().get()).isFalse();
    }

    @Test
    public void previous_guess_correct_is_true_if_guess_is_correct() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final Game gameAtStart = gameService.startGame(1, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        final Game gameAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameAfterGuess.getPreviousGuessCorrect().isPresent()).isTrue();
        assertThat(gameAfterGuess.getPreviousGuessCorrect().get()).isTrue();
    }

    @Test
    public void score_increases_if_guess_is_correct() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final Game gameAtStart = gameService.startGame(1, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        final Game gameAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameAfterGuess.getScore()).isEqualTo(1);
    }

    @Test
    public void game_is_over_after_guessing_one_and_only_phrase() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final Game gameAtStart = gameService.startGame(1, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        final Game gameAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameAfterGuess.isGameOver()).isTrue();
    }

    @Test
    public void phrase_is_removed_after_it_has_been_selected() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final Game gameAtStart = gameService.startGame(1, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        assertThat(gameAtStart.getPhrases()).doesNotContain(FOOTBALL_TEAM_PHRASE_1);
    }

    @Test
    public void next_phrase_is_selected_after_guessing_phrase() {
        when(phraseSelector.generateCategories(2, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1, GREETINGS_PHRASE_1));
        final Game gameAtStart = gameService.startGame(2, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        final Game gameStateAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);

        assertThat(gameStateAfterGuess.getCurrentPhrase().isPresent()).isTrue();
        assertThat(gameStateAfterGuess.getCurrentPhrase().get()).isEqualTo(GREETINGS_PHRASE_1);
    }

    @Test
    public void current_phrase_is_empty_if_game_is_over() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final Game gameAtStart = gameService.startGame(1, 1);

        assertThat(gameAtStart.getCurrentPhrase().isPresent()).isTrue();

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        final Game gameStateAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameStateAfterGuess.getCurrentPhrase().isPresent()).isFalse();
    }

    @Test
    public void current_category_is_empty_if_game_is_over() {
        when(phraseSelector.generateCategories(1, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1));
        final Game gameAtStart = gameService.startGame(1, 1);

        assertThat(gameAtStart.getCurrentCategory().isPresent()).isTrue();

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);
        final Game gameStateAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameStateAfterGuess.getCurrentCategory().isPresent()).isFalse();
    }

    @Test
    public void score_is_2_if_two_guesses_are_correct() {
        when(phraseSelector.generateCategories(2, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1, GREETINGS_PHRASE_1));
        final Game gameAtStart = gameService.startGame(2, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);

        gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        final Game gameAfterSecondGuess = gameService.guessPhrase(id, GREETING_1);
        assertThat(gameAfterSecondGuess.getScore()).isEqualTo(2);
    }

    @Test
    public void score_is_0_if_one_guess_is_correct_and_one_is_incorrect() {
        when(phraseSelector.generateCategories(2, 1)).thenReturn(createPhraseQueue(FOOTBALL_TEAM_PHRASE_1, GREETINGS_PHRASE_1));
        final Game gameAtStart = gameService.startGame(2, 1);

        final int id = gameAtStart.getId();
        when(gameRepository.findById(id)).thenReturn(gameAtStart);

        gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        final Game gameStateAfterSecondGuess = gameService.guessPhrase(id, "incorrect guess");
        assertThat(gameStateAfterSecondGuess.getScore()).isEqualTo(0);
    }

    private Queue<GamePhrase> createPhraseQueue(final GamePhrase... phrases) {
        return new LinkedList<GamePhrase>(Arrays.asList(phrases));
    }
}
