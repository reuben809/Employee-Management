package com.example.Employee_management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info().title("Employee Management Service").description("Employee Management API version ")
        )
                .servers(List.of(new Server().url("http://localhost:8080").description("local"),
                        new Server().url("http://localhost:8081").description("DEV")))

                .tags(List.of(
                        new Tag().name("Departments-Controller"),
                        new Tag().name("Employee-Controller"),
                        new Tag().name("Project-Controller"),
                        new Tag().name("Tasks")

                ));

    }
}
