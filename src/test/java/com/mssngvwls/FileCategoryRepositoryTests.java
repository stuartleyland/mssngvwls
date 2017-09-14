package com.mssngvwls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.Test;

public class FileCategoryRepositoryTests {

    @Test
    public void exception_is_thrown_if_file_doesnt_exist() {
        final String filename = "non-existant-file.txt";
        final FileCategoryRepository repository = new FileCategoryRepository(filename);

        assertThatExceptionOfType(RepositoryException.class)
                .isThrownBy(() -> repository.getAllCategories())
                .withMessage(FileCategoryRepository.ERROR_READING_FILE_MESSAGE_TEMPLATE, filename);
    }

    @Test
    public void expected_categories_are_returned() {
        final FileCategoryRepository repository = new FileCategoryRepository("football-teams.txt");
        final List<Category> categories = repository.getAllCategories();
        assertThat(categories.size()).isEqualTo(1);
    }

    @Test
    public void categories_have_expected_phrases() {
        final FileCategoryRepository repository = new FileCategoryRepository("football-teams.txt");

        final List<Category> categories = repository.getAllCategories();
        final Category footballTeamsCategory = categories.get(0);
        assertThat(footballTeamsCategory.getPhrases()).containsExactly(
                new PhraseBuilder().withFullPhrase("Manchester United").withCategory(footballTeamsCategory).build(),
                new PhraseBuilder().withFullPhrase("Liverpool").withCategory(footballTeamsCategory).build(),
                new PhraseBuilder().withFullPhrase("Hartlepool United").withCategory(footballTeamsCategory).build());
    }
}
