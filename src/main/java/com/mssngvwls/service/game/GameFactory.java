package com.mssngvwls.service.game;

import org.springframework.stereotype.Service;

import com.mssngvwls.service.repository.GameRepository;

@Service
public class GameFactory {

    private final GamePhraseSelector phraseSelector;
    private final GameRepository gameRepository;

    public GameFactory(final GamePhraseSelector phraseSelector, final GameRepository gameRepository) {
        this.phraseSelector = phraseSelector;
        this.gameRepository = gameRepository;
    }

    public GameService createGame() {
        return new GameService(phraseSelector, gameRepository);
    }
}
