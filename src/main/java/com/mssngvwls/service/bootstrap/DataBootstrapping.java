package com.mssngvwls.service.bootstrap;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mssngvwls.model.Category;
import com.mssngvwls.model.builder.CategoryBuilder;
import com.mssngvwls.service.repository.CategoryRepository;

@Service
public class DataBootstrapping implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBootstrapping.class);

    private final boolean shouldBootstrapData;
    private final CategoryRepository categoryRepository;

    public DataBootstrapping(@Value("${bootstrapData}") final boolean shouldBootstrapData, final CategoryRepository categoryRepository) {
        this.shouldBootstrapData = shouldBootstrapData;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("Checking if we need to bootstrap data");
        if (!shouldBootstrapData) {
            LOGGER.info("Not bootstrapping data");
            return;
        }

        LOGGER.info("Bootstrapping data");
        createCategoriesAndPhrases();
        LOGGER.info("Finished bootstrapping data");
    }

    private void createCategoriesAndPhrases() {
        final List<Category> categories = new ArrayList<>();

        categories.add(new CategoryBuilder()
                .withCategoryName("English Football Teams")
                .withPhrases("Manchester United", "Arsenal", "Liverpool", "Chelsea", "Everton", "Swansea", "Hartlepool United", "Torquay United",
                        "Manchester City", "Bournemouth", "West Bromwich Albion", "Queens Park Rangers", "Ipswich Town", "Burton Albion", "Oldham Athletic",
                        "Peterborough United")
                .build());

        categories.add(new CategoryBuilder()
                .withCategoryName("Cars")
                .withPhrases("Toyota Prius", "Toyota Yaris", "Toyota Corolla", "Nissan QASHQAI", "Ford Fiesta", "Ford Focus", "Ford Mondeo", "Ford Edge",
                        "Alfa Romeo 4C")
                .build());

        categories.add(new CategoryBuilder()
                .withCategoryName("Formula One Teams")
                .withPhrases("McLaren", "Williams", "Force India", "Benetton", "Minardi", "Spyker", "Red Bull Racing", "Toro Rosso", "Sauber", "Haas",
                        "Andrea Moda", "Caterham", "Honda", "Jordan", "Lotus", "Marussia", "Super Aguri")
                .build());

        categories.add(new CategoryBuilder()
                .withCategoryName("Greetings and their language")
                .withPhrases("Hello in English", "Bonjour in French", "Hola in Spanish", "Guten Tag in German", "Ciao in Italian", "Namaste in Hindi",
                        "Salaam in Persian")
                .build());

        categories.add(new CategoryBuilder()
                .withCategoryName("Rupaul contestants")
                .withPhrases("Tammie Brown", "Akashia", "Ongina", "BeBe Zahara Benet", "Sonique", "Tatianna", "Jujubee", "Mimi Imfurst", "Carmen Carrera",
                        "Raja", "Madame LaQueer", "Latrice Royale", "Sharon Needles", "Jade Jolie", "Alaska", "Adore Delano", "Miss Fame", "Aja")
                .build());

        categories.add(new CategoryBuilder()
                .withCategoryName("Historic Counties of England")
                .withPhrases("Essex", "Bedfordshire", "Derbyshire", "Yorkshire", "Kent", "Leicestershire", "Devon", "Dorset", "Gloucestershire", "Middlesex",
                        "Somerset", "Northumberland", "Rutland", "Shropshire", "Surrey", "Worcestershire", "Oxfordshire", "Northamptonshire")
                .build());

        categoryRepository.save(categories);
    }
}