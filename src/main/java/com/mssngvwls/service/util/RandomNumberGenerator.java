package com.mssngvwls.service.util;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class RandomNumberGenerator {

    public int between(final int lower, final int upper) {
        return ThreadLocalRandom.current().nextInt(lower, upper + 1);
    }
}
