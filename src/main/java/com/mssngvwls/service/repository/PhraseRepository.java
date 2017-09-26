package com.mssngvwls.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mssngvwls.model.Phrase;

public interface PhraseRepository extends JpaRepository<Phrase, Long> {
}
