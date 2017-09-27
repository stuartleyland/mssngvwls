package com.mssngvwls.service.game.test;

import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAMS_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_1_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_2_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mssngvwls.model.Category;
import com.mssngvwls.model.Game;
import com.mssngvwls.model.builder.CategoryBuilder;
import com.mssngvwls.service.game.GameService;
import com.mssngvwls.service.repository.CategoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceIntegrationTests {

    @Autowired
    private GameService gameService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void new_game_is_started() {
        final Category footballTeams = new CategoryBuilder()
                .withCategoryName(FOOTBALL_TEAMS_CATEGORY_NAME)
                .withPhrase(FOOTBALL_TEAM_1_NAME)
                .build();
        categoryRepository.save(footballTeams);

        final Game game = gameService.startGame(1, 1);

        assertThat(game).isNotNull();
        assertThat(game.getCurrentPhrase().isPresent()).isTrue();
        assertThat(game.getCurrentPhrase().get().getFullPhrase()).isEqualTo(FOOTBALL_TEAM_1_NAME);
        assertThat(game.getCurrentPhrase().get().getCategory()).isEqualTo(FOOTBALL_TEAMS_CATEGORY_NAME);
    }

    @Test
    public void game_is_updated_with_next_phrase_after_guessing() {
        final Category footballTeams = new CategoryBuilder()
                .withCategoryName(FOOTBALL_TEAMS_CATEGORY_NAME)
                .withPhrases(FOOTBALL_TEAM_1_NAME, FOOTBALL_TEAM_2_NAME)
                .build();
        categoryRepository.save(footballTeams);

        final Game game = gameService.startGame(1, 2);

        final Game gameAfterGuess = gameService.guessPhrase(game.getId(), FOOTBALL_TEAM_1_NAME);
        assertThat(gameAfterGuess.getPreviousGuessCorrect().get()).isTrue();
        assertThat(gameAfterGuess.getCurrentPhrase().get().getFullPhrase()).isEqualTo(FOOTBALL_TEAM_2_NAME);

    }
}
