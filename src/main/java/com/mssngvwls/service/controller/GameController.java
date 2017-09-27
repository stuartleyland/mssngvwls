package com.mssngvwls.service.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mssngvwls.model.Game;
import com.mssngvwls.service.controller.model.GameStartParameters;
import com.mssngvwls.service.controller.model.GuessPhraseParameters;
import com.mssngvwls.service.game.GameService;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(final GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseBody
    public Game startGame(@RequestBody final GameStartParameters parameters) {
        return gameService.startGame(parameters.getNumberOfCategories(), parameters.getNumberOfPhrasesPerCategory());
    }

    @RequestMapping(value = "/guess", method = RequestMethod.POST)
    @ResponseBody
    public Game guess(@RequestBody final GuessPhraseParameters parameters) {
        return gameService.guessPhrase(parameters.getGameId(), parameters.getGuess());
    }
}
