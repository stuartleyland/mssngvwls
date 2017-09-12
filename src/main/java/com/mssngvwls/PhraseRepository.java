package com.mssngvwls;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PhraseRepository {

    public List<Phrase> getPhrases() {
        return new ArrayList<>();
    }
}
