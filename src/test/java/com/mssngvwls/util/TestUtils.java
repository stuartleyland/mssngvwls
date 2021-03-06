package com.mssngvwls.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mssngvwls.model.Category;
import com.mssngvwls.model.builder.CategoryBuilder;
import com.mssngvwls.service.repository.CategoryRepository;

@Service
public class TestUtils {

    public static final String FOOTBALL_TEAMS_CATEGORY_NAME = "Football Teams";
    public static final String FOOTBALL_TEAM_1_NAME = "Manchester United";
    public static final String FOOTBALL_TEAM_2_NAME = "Arsenal";
    public static final String FOOTBALL_TEAM_3_NAME = "Hartlepool United";
    public static final String FOOTBALL_TEAM_4_NAME = "Swansea";

    public static final String GREETINGS_CATEGORY_NAME = "Greetings";
    public static final String GREETING_1 = "Yo";

    public static final String GOODBYES_CATEGORY_NAME = "Goodbyes";
    public static final String GOODBYES_1 = "Adios";
    public static final String GOODBYES_2 = "Auf Wiedersehen";
    public static final String GOODBYES_3 = "Cya";

    public static final String EUROPEAN_LANGUAGES_CATEGORY_NAME = "European Languages";
    public static final String EUROPEAN_LANGUAGES_1 = "French";
    public static final String EUROPEAN_LANGUAGES_2 = "German";
    public static final String EUROPEAN_LANGUAGES_3 = "Spanish";
    public static final String EUROPEAN_LANGUAGES_4 = "Italian";

    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategoryWithPhrases(final String categoryName, final String... phrases) {
        final Category footballTeams = new CategoryBuilder()
                .withCategoryName(categoryName)
                .withPhrases(phrases)
                .build();
        categoryRepository.save(footballTeams);
    }
}
