package com.mssngvwls.service.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mssngvwls.model.Phrase;

@Service
public class PhraseRepository {

    public List<Phrase> getPhrases() {
        return new ArrayList<>();
    }
}
