package com.songify.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        final String securityScheme = "bearerAuth";
        return new OpenAPI().addSecurityItem(
                                    new SecurityRequirement().addList(securityScheme))
                            .components(
                                    new Components().addSecuritySchemes(securityScheme,
                                                                        new SecurityScheme().name(securityScheme)
                                                                                            .type(SecurityScheme.Type.HTTP)
                                                                                            .scheme("bearer")
                                                                                            .bearerFormat("JWT")));
    }
}
