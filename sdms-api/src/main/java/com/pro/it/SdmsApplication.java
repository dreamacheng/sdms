package com.pro.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SdmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SdmsApplication.class, args);
    }

}
