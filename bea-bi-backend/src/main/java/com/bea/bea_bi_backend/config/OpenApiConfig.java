package com.bea.bea_bi_backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    private static final String SECURITY_SCHEME_NAME = "keycloak_oauth2";

    @Bean
    public OpenAPI openAPI() {

        String tokenUrl = issuerUri + "/protocol/openid-connect/token";

        return new OpenAPI()
                .info(new Info()
                        .title("BEA BI – API Décisionnelle RH & Crédit")
                        .version("1.0.0")
                        .description("API analytique pour les dashboards RH et Crédit de la BEA")
                        .contact(new Contact()
                                .name("BEA – Équipe Projet BI")
                                .email("contact@bea.dz")))

                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .description("Authentification via Keycloak")
                                        .flows(new OAuthFlows()
                                                // ← Password flow : pas de redirect, pas de PKCE
                                                .password(new OAuthFlow()
                                                        .tokenUrl(tokenUrl)
                                                )
                                        )
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME));
    }
}