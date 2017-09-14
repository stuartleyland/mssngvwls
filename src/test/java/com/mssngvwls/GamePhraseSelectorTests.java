package com.mssngvwls;

import static com.mssngvwls.TestUtils.EUROPEAN_LANGUAGES_1;
import static com.mssngvwls.TestUtils.EUROPEAN_LANGUAGES_2;
import static com.mssngvwls.TestUtils.EUROPEAN_LANGUAGES_3;
import static com.mssngvwls.TestUtils.EUROPEAN_LANGUAGES_4;
import static com.mssngvwls.TestUtils.EUROPEAN_LANGUAGES_CATEGORY_NAME;
import static com.mssngvwls.TestUtils.FOOTBALL_TEAMS_CATEGORY_NAME;
import static com.mssngvwls.TestUtils.FOOTBALL_TEAM_1_NAME;
import static com.mssngvwls.TestUtils.FOOTBALL_TEAM_2_NAME;
import static com.mssngvwls.TestUtils.FOOTBALL_TEAM_3_NAME;
import static com.mssngvwls.TestUtils.FOOTBALL_TEAM_4_NAME;
import static com.mssngvwls.TestUtils.GOODBYES_1;
import static com.mssngvwls.TestUtils.GOODBYES_2;
import static com.mssngvwls.TestUtils.GOODBYES_3;
import static com.mssngvwls.TestUtils.GOODBYES_CATEGORY_NAME;
import static com.mssngvwls.TestUtils.GREETINGS_CATEGORY_NAME;
import static com.mssngvwls.TestUtils.GREETING_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GamePhraseSelectorTests {

    private static final String PHRASE_WITHOUT_VOWELS = "mssngvwls";

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private PhraseFormatter phraseFormatter;

    @InjectMocks
    private GamePhraseSelector phraseSelector;

    @Before
    public void setup() {
        when(phraseFormatter.format(anyString())).thenReturn(PHRASE_WITHOUT_VOWELS);
    }

    @Test
    public void game_phrases_are_generated_if_categories_and_phrases_meet_game_criteria() {
        final Category category = new CategoryBuilder()
                .withCategoryName(FOOTBALL_TEAMS_CATEGORY_NAME)
                .withPhrase(FOOTBALL_TEAM_1_NAME)
                .build();
        when(categoryRepository.getAllCategories()).thenReturn(Arrays.asList(category));

        final Queue<GamePhrase> gamePhrases = phraseSelector.generateCategories(1, 1);
        assertThat(gamePhrases).containsExactly(new GamePhrase(FOOTBALL_TEAM_1_NAME, PHRASE_WITHOUT_VOWELS, FOOTBALL_TEAMS_CATEGORY_NAME));
    }

    @Test
    public void category_is_excluded_if_there_are_not_enough_phrases() {
        final Category footballTeams = new CategoryBuilder()
                .withCategoryName(FOOTBALL_TEAMS_CATEGORY_NAME)
                .withPhrase(FOOTBALL_TEAM_1_NAME)
                .withPhrase(FOOTBALL_TEAM_2_NAME)
                .build();

        final Category greetings = new CategoryBuilder()
                .withCategoryName(GREETINGS_CATEGORY_NAME)
                .withPhrase(GREETING_1)
                .build();

        when(categoryRepository.getAllCategories()).thenReturn(Arrays.asList(footballTeams, greetings));

        final Queue<GamePhrase> gamePhrases = phraseSelector.generateCategories(2, 2);

        final Queue<GamePhrase> expectedGamePhrases = new GamePhraseQueueBuilder()
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_1_NAME, PHRASE_WITHOUT_VOWELS)
                .withGamePhraseFor(FOOTBALL_TEAMS_CATEGORY_NAME, FOOTBALL_TEAM_2_NAME, PHRASE_WITHOUT_VOWELS)
                .build();
        assertThat(gamePhrases).isEqualTo(expectedGamePhrases);
    }

    @Test
    public void no_categories_are_returned_if_there_are_not_enough_phrases() {
        final Category footballTeams = new CategoryBuilder()
                .withCategoryName(FOOTBALL_TEAMS_CATEGORY_NAME)
                .build();

        final Category greetings = new CategoryBuilder()
                .withCategoryName(GREETINGS_CATEGORY_NAME)
                .build();

        when(categoryRepository.getAllCategories()).thenReturn(Arrays.asList(footballTeams, greetings));

        final Queue<GamePhrase> gamePhrases = phraseSelector.generateCategories(2, 2);

        final Queue<GamePhrase> expectedGamePhrases = new GamePhraseQueueBuilder().build();
        assertThat(gamePhrases).isEqualTo(expectedGamePhrases);
    }

    @Test
    public void first_matching_categories_are_used() {
        final Category footballTeams = new CategoryBuilder()
                .withCategoryName(FOOTBALL_TEAMS_CATEGORY_NAME)
                .withPhrases(FOOTBALL_TEAM_1_NAME, FOOTBALL_TEAM_2_NAME)
                .build();

        final Category greetings = new CategoryBuilder()
                .withCategoryName(GREETINGS_CATEGORY_NAME)
                .withPhrase(GREETING_1)
                .build();

        final Category goodbyes = new CategoryBuilder()
                .withCategoryName(GOODBYES_CATEGORY_NAME)
                .withPhrases(GOODBYES_1, GOODBYES_2, GOODBYES_3)
                .build();

        final Category europeanLanguages = new CategoryBuilder()
                .withCategoryName(EUROPEAN_LANGUAGES_CATEGORY_NAME)
                .withPhrases(EUROPEAN_LANGUAGES_1, EUROPEAN_LANGUAGES_2, EUROPEAN_LANGUAGES_3, EUROPEAN_LANGUAGES_4)
                .build();

        when(categoryRepository.getAllCategories()).thenReturn(Arrays.asList(footballTeams, greetings, goodbyes, europeanLanguages));

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
        final Category footballTeams = new CategoryBuilder()
                .withCategoryName(FOOTBALL_TEAMS_CATEGORY_NAME)
                .withPhrases(FOOTBALL_TEAM_1_NAME, FOOTBALL_TEAM_2_NAME, FOOTBALL_TEAM_3_NAME, FOOTBALL_TEAM_4_NAME)
                .build();

        final Category greetings = new CategoryBuilder()
                .withCategoryName(GREETINGS_CATEGORY_NAME)
                .withPhrase(GREETING_1)
                .build();

        final Category europeanLanguages = new CategoryBuilder()
                .withCategoryName(EUROPEAN_LANGUAGES_CATEGORY_NAME)
                .withPhrases(EUROPEAN_LANGUAGES_1, EUROPEAN_LANGUAGES_2, EUROPEAN_LANGUAGES_3, EUROPEAN_LANGUAGES_4)
                .build();

        when(categoryRepository.getAllCategories()).thenReturn(Arrays.asList(footballTeams, greetings, europeanLanguages));

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