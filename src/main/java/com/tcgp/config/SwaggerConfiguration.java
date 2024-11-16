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
                        .description("This is the API documentation for the Arcade Web Manager application.")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Support Team")
                                .url("http://example.com/contact")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}