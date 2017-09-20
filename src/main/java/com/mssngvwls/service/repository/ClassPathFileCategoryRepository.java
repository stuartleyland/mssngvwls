package com.mssngvwls.service.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.mssngvwls.model.Category;
import com.mssngvwls.model.builder.CategoryBuilder;

@Service
public class ClassPathFileCategoryRepository implements CategoryRepository {

    public static final String ERROR_READING_FILE_MESSAGE_TEMPLATE = "Error reading file [%s]";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathFileCategoryRepository.class);

    private volatile List<Category> categories = null;
    private final Object lock = new Object();

    private final String filename;

    public ClassPathFileCategoryRepository(@Value("${categoryFileLocation}") final String filename) {
        this.filename = filename;
    }

    @Override
    public List<Category> getAllCategories() {
        if (categories == null) {
            synchronized (lock) {
                if (categories == null) {
                    final List<Category> tmpCategories = parseCategoriesFile();
                    categories = Collections.unmodifiableList(tmpCategories);
                }
            }
        }
        return categories;
    }

    private List<Category> parseCategoriesFile() {
        final List<Category> fileCategories = new ArrayList<>();

        final ClassPathResource categoriesFileResource = new ClassPathResource(filename);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(categoriesFileResource.getInputStream()))) {
            String line = "";
            while ((line = in.readLine()) != null) {
                final String[] lineParts = line.split("\\|");
                if (lineParts.length > 1) {
                    final String categoryName = lineParts[0];
                    final List<String> phrases = Arrays.asList(lineParts).subList(1, lineParts.length);
                    final Category category = new CategoryBuilder()
                            .withCategoryName(categoryName)
                            .withPhrases(phrases)
                            .build();
                    fileCategories.add(category);
                }
            }
        } catch (final IOException ioe) {
            final String errorMessage = String.format(ERROR_READING_FILE_MESSAGE_TEMPLATE, filename);
            LOGGER.error(errorMessage, ioe);
            throw new RepositoryException(errorMessage);
        }

        return fileCategories;
    }
}
