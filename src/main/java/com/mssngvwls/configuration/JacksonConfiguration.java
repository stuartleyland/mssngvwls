package com.mssngvwls.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Module jacksonJdk8Module() {
        return new Jdk8Module();
    }
}
