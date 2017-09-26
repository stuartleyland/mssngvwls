package com.mssngvwls.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mssngvwls.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

}
