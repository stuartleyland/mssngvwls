package com.mssngvwls.service.repository;

import org.springframework.stereotype.Service;

import com.mssngvwls.model.Game;

@Service
public class GameRepository {

    public Game findById(final int id) {
        return new Game();
    }
}
