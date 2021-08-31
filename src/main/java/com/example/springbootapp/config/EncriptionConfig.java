package com.example.springbootapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class EncriptionConfig {
    @Bean
    public PasswordEncoder getBcryptEncoder(){
        return new BCryptPasswordEncoder(8);
    }

}
