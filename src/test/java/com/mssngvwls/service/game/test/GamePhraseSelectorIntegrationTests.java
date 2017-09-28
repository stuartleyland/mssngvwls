package com.mssngvwls.service.game.test;

import static com.mssngvwls.util.TestUtils.EUROPEAN_LANGUAGES_1;
import static com.mssngvwls.util.TestUtils.EUROPEAN_LANGUAGES_2;
import static com.mssngvwls.util.TestUtils.EUROPEAN_LANGUAGES_3;
import static com.mssngvwls.util.TestUtils.EUROPEAN_LANGUAGES_4;
import static com.mssngvwls.util.TestUtils.EUROPEAN_LANGUAGES_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAMS_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_1_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_2_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_3_NAME;
import static com.mssngvwls.util.TestUtils.FOOTBALL_TEAM_4_NAME;
import static com.mssngvwls.util.TestUtils.GOODBYES_1;
import static com.mssngvwls.util.TestUtils.GOODBYES_2;
import static com.mssngvwls.util.TestUtils.GOODBYES_3;
import static com.mssngvwls.util.TestUtils.GOODBYES_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.GREETINGS_CATEGORY_NAME;
import static com.mssngvwls.util.TestUtils.GREETING_1;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Queue;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mssngvwls.model.GamePhrase;
import com.mssngvwls.model.builder.GamePhraseBuilder;
import com.mssngvwls.service.game.GamePhraseSelector;
import com.mssngvwls.util.GamePhraseQueueBuilder;
import com.mssngvwls.util.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GamePhraseSelectorIntegrationTests {

    private static final String PHRASE_WITHOUT_VOWELS = "mssngvwls";

    @Autowired
    private GamePhraseSelector phraseSelector;

    @Autowired
    private TestUtils testUtils;

    @Test
    public void game_phrases_are_generated_if_categories_and_phrases_meet_game_criteria() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME);

        final Queue<GamePhrase> gamePhrases = phraseSelector.generateCategories(1, 1);
        assertThat(gamePhrases).containsExactly(new GamePhraseBuilder()
                .withCategory(FOOTBALL_TEAMS_CATEGORY_NAME)
                .withFullPhrase(FOOTBALL_TEAM_1_NAME)
                .withPhraseWithoutVowels(PHRASE_WITHOUT_VOWELS)
                .build());
    }

    @Test
    public void category_is_excluded_if_there_are_not_enough_phrases() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME, FOOTBALL_TEAM_2_NAME);
        testUtils.createCategoryWithPhrases(GREETINGS_CATEGORY_NAME, GREETING_1);

        final Queue<GamePhrase> gamePhrases = phraseSelector.generateCategories(2, 2);

        final Queue<GamePhrase> expectedGamePhrases = new GamePhraseQueueBuilder()
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_2_NAME, PHRASE_WITHOUT_VOWELS)
                .build();
        assertThat(gamePhrases).isEqualTo(expectedGamePhrases);
    }

    @Test
    public void no_categories_are_returned_if_there_are_not_enough_phrases() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, new String[] {});
        testUtils.createCategoryWithPhrases(GREETINGS_CATEGORY_NAME, new String[] {});

        final Queue<GamePhrase> gamePhrases = phraseSelector.generateCategories(2, 2);

        final Queue<GamePhrase> expectedGamePhrases = new GamePhraseQueueBuilder().build();
        assertThat(gamePhrases).isEqualTo(expectedGamePhrases);
    }

    @Test
    public void first_matching_categories_are_used() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME, FOOTBALL_TEAM_2_NAME);
        testUtils.createCategoryWithPhrases(GREETINGS_CATEGORY_NAME, GREETING_1);
        testUtils.createCategoryWithPhrases(GOODBYES_CATEGORY_NAME, GOODBYES_1, GOODBYES_2, GOODBYES_3);
        testUtils.createCategoryWithPhrases(EUROPEAN_LANGUAGES_CATEGORY_NAME, EUROPEAN_LANGUAGES_1, EUROPEAN_LANGUAGES_2, EUROPEAN_LANGUAGES_3,
                EUROPEAN_LANGUAGES_4);

        final Queue<GamePhrase> gamePhrases = phraseSelector.generateCategories(2, 2);

        final Queue<GamePhrase> expectedGamePhrases = new GamePhraseQueueBuilder()
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_2_NAME, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(GOODBYES_CATEGORY_NAME, GOODBYES_1, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(GOODBYES_CATEGORY_NAME, GOODBYES_2, PHRASE_WITHOUT_VOWELS)
                .build();
        assertThat(gamePhrases).isEqualTo(expectedGamePhrases);
    }

    @Test
    public void correct_number_of_phrases_for_matching_categories_are_returned() {
        testUtils.createCategoryWithPhrases(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME, FOOTBALL_TEAM_2_NAME, FOOTBALL_TEAM_3_NAME,
                FOOTBALL_TEAM_4_NAME);
        testUtils.createCategoryWithPhrases(GREETINGS_CATEGORY_NAME, GREETING_1);
        testUtils.createCategoryWithPhrases(EUROPEAN_LANGUAGES_CATEGORY_NAME, EUROPEAN_LANGUAGES_1, EUROPEAN_LANGUAGES_2, EUROPEAN_LANGUAGES_3,
                EUROPEAN_LANGUAGES_4);

        final Queue<GamePhrase> gamePhrases = phraseSelector.generateCategories(2, 3);

        final Queue<GamePhrase> expectedGamePhrases = new GamePhraseQueueBuilder()
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_2_NAME, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_3_NAME, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(EUROPEAN_LANGUAGES_CATEGORY_NAME, EUROPEAN_LANGUAGES_1, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(EUROPEAN_LANGUAGES_CATEGORY_NAME, EUROPEAN_LANGUAGES_2, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(EUROPEAN_LANGUAGES_CATEGORY_NAME, EUROPEAN_LANGUAGES_3, PHRASE_WITHOUT_VOWELS)
                .build();
        assertThat(gamePhrases).isEqualTo(expectedGamePhrases);
    }
}