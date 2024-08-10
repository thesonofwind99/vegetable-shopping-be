package com.vegetableshoppingbe;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
@Log4j2
public class VegetableShoppingBeApplication {

    private final Environment env;

    public static void main(String[] args) {
        SpringApplication.run(VegetableShoppingBeApplication.class, args);
    }

    @PostConstruct
    public void logApplicationProperties() {
        log.info("{}={}", "spring.datasource.password",
                env.getProperty("spring.datasource.password"));
        log.info("{}={}", "spring.datasource.username",
                env.getProperty("spring.datasource.username"));
    }
}
