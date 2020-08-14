package com.crud.tasks.config;

import com.crud.tasks.domain.Badges;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableScheduling//umozliwia ustawienie interwału dla czynności np. wysyłce maila
@EnableSwagger2 //obsługa swwagera
@Configuration
public class CoreConfiguration implements WebMvcConfigurer { //pozwala na implementacje metody udostepniającej statyczne dane(html,js.css-widoki)

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }////umożliwia realizowanie żądań HTTP pomiędzy serwerami. Dzięki niej możemy wykonywać różnego rodzaju żądania takie jak np. GET, POST,
    // i natychmiast przetwarzać odpowiedź serwera na obiekt w javie

    @Bean public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.crud.tasks.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) { //nadajemy Springowi prawa do tego, aby przeszukał katalogi w celu udostępnienia ich użytkownikowi naszej aplikacji.
        // Swagger zawiera w sobie już gotowe pliki HTML, CSS i JS, które muszą mieć możliwość “ujawnienia się”
        // Required by Swagger UI configuration
        registry.addResourceHandler("/lib/**").addResourceLocations("/lib/").setCachePeriod(0);
        registry.addResourceHandler("/images/**").addResourceLocations("/images/").setCachePeriod(0);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(0);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

