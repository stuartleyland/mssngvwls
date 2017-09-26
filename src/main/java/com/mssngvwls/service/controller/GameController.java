package com.mssngvwls.service.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mssngvwls.model.Game;
import com.mssngvwls.model.GameStartParameters;
import com.mssngvwls.service.game.GameService;
import com.mssngvwls.service.game.GameFactory;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameFactory gameFactory;

    public GameController(final GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public Game startGame(@RequestBody final GameStartParameters parameters) {
        final GameService game = gameFactory.createGame();
        return game.startGame(parameters.getNumberOfCategories(), parameters.getNumberOfPhrasesPerCategory());
    }

    @RequestMapping(value = "/guess", method = RequestMethod.POST)
    @ResponseBody
    public Game guess() {
        return null;
    }
}
