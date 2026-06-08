package com.devsu.clientes.infrastructure.config;

import com.devsu.clientes.application.port.out.PasswordHasherPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordHasherPort passwordHasherPort() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder::encode;
    }
}
