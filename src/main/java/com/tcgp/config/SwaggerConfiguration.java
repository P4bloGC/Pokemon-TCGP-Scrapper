package com.tcgp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pokemon TCGP Scrapper")
                        .version("1.0.0")
                        .description("API para extraer información de cartas desde Pokémon TCG Pocket utilizando Spring Boot y Jsoup.")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Pablo González")
                                .url("https://github.com/P4bloGC/Pokemon-TCGP-Scrappe")
                                .email("pablo.gc662@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}