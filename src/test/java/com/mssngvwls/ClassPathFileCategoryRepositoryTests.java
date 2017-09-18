package com.mssngvwls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.Test;

public class ClassPathFileCategoryRepositoryTests {

    @Test
    public void exception_is_thrown_if_file_doesnt_exist() {
        final String filename = "non-existant-file.txt";
        final ClassPathFileCategoryRepository repository = new ClassPathFileCategoryRepository(filename);

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.getAllCategories())
                .withMessage(ClassPathFileCategoryRepository.ERROR_READING_FILE_MESSAGE_TEMPLATE, filename);
    }

    @Test
    public void expected_categories_are_returned() {
        final ClassPathFileCategoryRepository repository = new ClassPathFileCategoryRepository("football-teams.txt");
        final List<Category> categories = repository.getAllCategories();
        assertThat(categories.size()).isEqualTo(1);
    }

    @Test
    public void categories_have_expected_phrases() {
        final ClassPathFileCategoryRepository repository = new ClassPathFileCategoryRepository("football-teams.txt");

        final List<Category> categories = repository.getAllCategories();
        final Category footballTeamsCategory = categories.get(0);
        assertThat(footballTeamsCategory.getPhrases()).containsExactly(
                new PhraseBuilder().withFullPhrase("Manchester United").withCategory(footballTeamsCategory).build(),
                new PhraseBuilder().withFullPhrase("Liverpool").withCategory(footballTeamsCategory).build(),
                new PhraseBuilder().withFullPhrase("Hartlepool United").withCategory(footballTeamsCategory).build());
    }

    @Test
    public void multiple_categories_are_parsed() {
        final ClassPathFileCategoryRepository repository = new ClassPathFileCategoryRepository("multiple-categories.txt");
        final List<Category> categories = repository.getAllCategories();

        final Category footballTeamsCategory = new CategoryBuilder()
                .withCategoryName("Football Teams")
                .withPhrases("Manchester United", "Liverpool", "Hartlepool United").build();

        final Category languagesCategory = new CategoryBuilder()
                .withCategoryName("Languages")
                .withPhrases("English", "Italian", "French", "German")
                .build();

        assertThat(categories).containsExactly(footballTeamsCategory, languagesCategory);
    }
}
