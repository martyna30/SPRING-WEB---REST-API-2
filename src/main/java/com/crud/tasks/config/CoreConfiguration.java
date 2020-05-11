package com.crud.tasks.config;

import com.crud.tasks.domain.Badges;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfiguration {

    @Bean
    public RestTemplate restTemplate() {//umożliwia realizowanie żądań HTTP pomiędzy serwerami. Dzięki niej możemy wykonywać różnego rodzaju żądania takie jak np. GET, POST,
        // i natychmiast przetwarzać odpowiedź serwera na obiekt
        return new RestTemplate();
    }
}
