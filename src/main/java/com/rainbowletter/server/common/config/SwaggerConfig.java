package com.rainbowletter.server.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
    servers = {
        @Server(url = "http://localhost:8080", description = "Local")
    }
)
@Configuration
@Profile("local")
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("v1")
            .pathsToMatch("/**")
            .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        Components components = new Components()
            .addSecuritySchemes(HttpHeaders.AUTHORIZATION, new SecurityScheme()
                .name(HttpHeaders.AUTHORIZATION)
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT"));

        return new OpenAPI()
            .info(new Info()
                .title("무지개편지")
                .description("무지개편지 REST API")
                .version("v1"))
            .addSecurityItem(new SecurityRequirement()
                .addList(HttpHeaders.AUTHORIZATION))
            .components(components);
    }

}