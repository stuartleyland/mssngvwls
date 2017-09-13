package com.mssngvwls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Queue;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MssngvwlsApplication implements CommandLineRunner {

    private final GameFactory gameFactory;

    public MssngvwlsApplication(final GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    public static void main(final String[] args) {
        SpringApplication.run(MssngvwlsApplication.class, args);
    }

    @Override
    public void run(final String... arg0) throws Exception {
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        final Game game = gameFactory.createGame();
        GameState gameState = game.startGame(2, 2);
        final Queue<GamePhrase> gamePhrases = gameState.getPhrases();
        System.out.println("Number of Phrases: " + gamePhrases.size());

        while (!gameState.isGameOver()) {
            displayGameState(gameState);
            final String guess = br.readLine();
            gameState = game.guessPhrase(guess);
        }

        System.out.println("*** Game Over ***");
        displayGameState(gameState);
    }

    private void displayGameState(final GameState gameState) {
        final Optional<Boolean> previousGuessCorrect = gameState.getPreviousGuessCorrect();
        if (previousGuessCorrect.isPresent()) {
            System.out.println("Previous guess correct? " + previousGuessCorrect.get());
        }

        final Optional<String> currentCategory = gameState.getCurrentCategory();
        System.out.println("Category: " + (currentCategory.isPresent() ? currentCategory.get() : "<no category>"));
        final Optional<GamePhrase> currentPhrase = gameState.getCurrentPhrase();
        System.out
                .println("Phrase: " + (currentPhrase.isPresent() ? currentPhrase.get().getPhraseWithoutVowels() : "<no phrase>"));
        System.out.println("Score: " + gameState.getScore());
    }
}
