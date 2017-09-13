package com.mssngvwls;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FileCategoryRepository implements CategoryRepository {

    public static final String FOOTBALL_TEAMS_CATEGORY_NAME = "Football Teams";
    public static final String FOOTBALL_TEAM_1_NAME = "Manchester United";
    public static final String FOOTBALL_TEAM_2_NAME = "Arsenal";

    public static final String GREETINGS_CATEGORY_NAME = "Greetings";
    public static final String GREETING_1 = "Yo";

    public static final String GOODBYES_CATEGORY_NAME = "Goodbyes";
    public static final String GOODBYES_1 = "Adios";
    public static final String GOODBYES_2 = "Auf Wiedersehen";
    public static final String GOODBYES_3 = "Cya";

    @Override
    public List<Category> getAllCategories() {
        final Category footballTeams = new CategoryBuilder()
                .withCategoryName(FOOTBALL_TEAMS_CATEGORY_NAME)
                .withPhrase(FOOTBALL_TEAM_1_NAME)
                .withPhrase(FOOTBALL_TEAM_2_NAME)
                .build();

        final Category greetings = new CategoryBuilder()
                .withCategoryName(GREETINGS_CATEGORY_NAME)
                .withPhrase(GREETING_1)
                .build();

        final Category goodbyes = new CategoryBuilder()
                .withCategoryName(GOODBYES_CATEGORY_NAME)
                .withPhrase(GOODBYES_1)
                .withPhrase(GOODBYES_2)
                .withPhrase(GOODBYES_3)
                .build();

        return Arrays.asList(footballTeams, greetings, goodbyes);
    }

}
