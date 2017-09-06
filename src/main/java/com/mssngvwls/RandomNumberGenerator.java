package com.mssngvwls;

import org.springframework.stereotype.Service;

@Service
public interface RandomNumberGenerator {

    public int between(final int lower, final int upper);
}
