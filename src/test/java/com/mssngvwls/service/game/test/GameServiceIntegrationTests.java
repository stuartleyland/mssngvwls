package com.mssngvwls.service.game.test;

import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAMS_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_1_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_2_NAME;
import static com.mssngvwls.util.TestUtils.GREETINGS_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.GREETING_1;
import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mssngvwls.model.Game;
import com.mssngvwls.service.game.GameService;
import com.mssngvwls.util.TestUtils;
import com.mssngvwls.util.assertion.GamePhraseAssert;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GameServiceIntegrationTests {

    private static final String INCORRECT_ANSWER = "incorrect answer";

    @Autowired
    private GameService gameService;

    @Autowired
    private TestUtils testUtils;

    @Test
    public void new_game_is_started() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);

        final Game game = gameService.startGame(1, 1);

        assertThat(game).isNotNull();
        assertThat(game.getCurrentPhrase().isPresent()).isTrue();
        assertThat(game.getCurrentPhrase().get().getFullPhrase()).isEqualTo(FOOTBALL_TEAM_1_NAME);
        assertThat(game.getCurrentPhrase().get().getCategory()).isEqualTo(FOOTBALL_TEAMS_CATEGORY_NAME);
    }

    @Test
    public void game_is_updated_with_next_phrase_after_guessing() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME, FOOTBALL_TEAM_2_NAME);

        final Game game = gameService.startGame(1, 2);

        final Game gameAfterGuess = gameService.guessPhrase(game.getId(), FOOTBALL_TEAM_1_NAME);
        assertThat(gameAfterGuess.getCurrentPhrase().isPresent()).isTrue();
    }

    @Test
    public void score_is_initialised_to_zero() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.getScore()).isEqualTo(0);
    }

    @Test
    public void current_category_is_initialised_on_game_start() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.getCurrentCategory().isPresent()).isTrue();
        assertThat(gameState.getCurrentCategory().get()).isEqualTo(FOOTBALL_TEAMS_CATEGORY_NAME);
    }

    @Test
    public void current_phrase_is_initialised_on_game_start() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.getCurrentPhrase().isPresent()).isTrue();
        assertThat(gameState.getCurrentPhrase().get().getCategory()).isEqualTo(FOOTBALL_TEAMS_CATEGORY_NAME);
        assertThat(gameState.getCurrentPhrase().get().getFullPhrase()).isEqualTo(FOOTBALL_TEAM_1_NAME);
    }

    @Test
    public void previous_guess_correct_is_not_set_on_game_start() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.getPreviousGuessCorrect().isPresent()).isFalse();
    }

    @Test
    public void game_is_not_over_if_game_is_started_with_a_phrase() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);

        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.isGameOver()).isFalse();
    }

    @Test
    public void game_is_over_if_no_phrases_are_supplied() {
        final Game gameState = gameService.startGame(1, 1);

        assertThat(gameState.isGameOver()).isTrue();
    }

    @Test
    public void score_decreases_if_guess_is_incorrect() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        final Game gameAtStart = gameService.startGame(1, 1);

        final Long id = gameAtStart.getId();
        final Game gameAfterGuess = gameService.guessPhrase(id, INCORRECT_ANSWER);
        assertThat(gameAfterGuess.getScore()).isEqualTo(-1);
    }

    @Test
    public void previous_guess_correct_is_false_if_guess_is_incorrect() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        final Game gameAtStart = gameService.startGame(1, 1);

        final Long id = gameAtStart.getId();
        final Game gameAfterGuess = gameService.guessPhrase(id, INCORRECT_ANSWER);
        assertThat(gameAfterGuess.getPreviousGuessCorrect().isPresent()).isTrue();
        assertThat(gameAfterGuess.getPreviousGuessCorrect().get()).isFalse();
    }

    @Test
    public void previous_guess_correct_is_true_if_guess_is_correct() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        final Game gameAtStart = gameService.startGame(1, 1);

        final Long id = gameAtStart.getId();
        final Game gameAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameAfterGuess.getPreviousGuessCorrect().isPresent()).isTrue();
        assertThat(gameAfterGuess.getPreviousGuessCorrect().get()).isTrue();
    }

    @Test
    public void score_increases_if_guess_is_correct() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        final Game gameAtStart = gameService.startGame(1, 1);

        final Long id = gameAtStart.getId();
        final Game gameAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameAfterGuess.getScore()).isEqualTo(1);
    }

    @Test
    public void game_is_over_after_guessing_one_and_only_phrase() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        final Game gameAtStart = gameService.startGame(1, 1);

        final Long id = gameAtStart.getId();
        final Game gameAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameAfterGuess.isGameOver()).isTrue();
    }

    @Test
    public void phrase_is_removed_after_it_has_been_selected() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        final Game gameAtStart = gameService.startGame(1, 1);

        GamePhraseAssert.assertThat(gameAtStart.getPhrases()).doesNotHavePhrase(FOOTBALL_TEAM_1_NAME);
    }

    @Test
    public void next_phrase_is_selected_after_guessing_phrase() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        testUtils.createCategoryWithPhrases(GREETINGS_CATEGORY_NAME, GREETING_1);
        final Game gameAtStart = gameService.startGame(2, 1);

        final Long id = gameAtStart.getId();
        final Game gameStateAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);

        assertThat(gameStateAfterGuess.getCurrentPhrase().isPresent()).isTrue();
        assertThat(gameStateAfterGuess.getCurrentPhrase().get().getFullPhrase()).isEqualTo(GREETING_1);
    }

    @Test
    public void current_phrase_is_empty_if_game_is_over() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        final Game gameAtStart = gameService.startGame(1, 1);

        assertThat(gameAtStart.getCurrentPhrase().isPresent()).isTrue();

        final Long id = gameAtStart.getId();
        final Game gameStateAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameStateAfterGuess.getCurrentPhrase().isPresent()).isFalse();
    }

    @Test
    public void current_category_is_empty_if_game_is_over() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        final Game gameAtStart = gameService.startGame(1, 1);

        assertThat(gameAtStart.getCurrentCategory().isPresent()).isTrue();

        final Long id = gameAtStart.getId();
        final Game gameStateAfterGuess = gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        assertThat(gameStateAfterGuess.getCurrentCategory().isPresent()).isFalse();
    }

    @Test
    public void score_is_2_if_two_guesses_are_correct() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        testUtils.createCategoryWithPhrases(GREETINGS_CATEGORY_NAME, GREETING_1);
        final Game gameAtStart = gameService.startGame(2, 1);

        final Long id = gameAtStart.getId();
        gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        final Game gameAfterSecondGuess = gameService.guessPhrase(id, GREETING_1);
        assertThat(gameAfterSecondGuess.getScore()).isEqualTo(2);
    }

    @Test
    public void score_is_0_if_one_guess_is_correct_and_one_is_incorrect() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);
        testUtils.createCategoryWithPhrases(GREETINGS_CATEGORY_NAME, GREETING_1);
        final Game gameAtStart = gameService.startGame(2, 1);

        final Long id = gameAtStart.getId();
        gameService.guessPhrase(id, FOOTBALL_TEAM_1_NAME);
        final Game gameStateAfterSecondGuess = gameService.guessPhrase(id, "incorrect guess");
        assertThat(gameStateAfterSecondGuess.getScore()).isEqualTo(0);
    }
}
