package com.mssngvwls.service.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mssngvwls.model.GameStartParameters;
import com.mssngvwls.service.game.Game;
import com.mssngvwls.service.game.GameFactory;
import com.mssngvwls.service.game.GameState;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameFactory gameFactory;

    public GameController(final GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public GameState startGame(@RequestBody final GameStartParameters parameters) {
        final Game game = gameFactory.createGame();
        return game.startGame(parameters.getNumberOfCategories(), parameters.getNumberOfPhrasesPerCategory());
    }
}
