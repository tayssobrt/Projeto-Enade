package com.ads.enade.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    private final String securityFormat = "Bearer Authentication";

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("ENADE API Documentation")
                        .description("""
                                API REST para a plataforma ENADE: autenticação, geração de simulados, \
                                cadastro de questões e acompanhamento de desempenho do usuário.
                                
                                Endpoints marcados com o cadeado exigem token JWT no header \
                                `Authorization: Bearer {token}`, obtido em `POST /api/auth/login`.
                                """)
                        .version("v1.0")
                        .contact(new Contact().name("Enade Suporte").email("suporte.enade@gmail.com"))
                )
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Ambiente local")
                        // adicionar aqui o servidor de produção/homologação quando existir
                ))
                .addSecurityItem(new SecurityRequirement().addList(securityFormat))
                .components(new Components().addSecuritySchemes(securityFormat, createApiKeyOpenApi()));
    }

    private SecurityScheme createApiKeyOpenApi(){
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("Bearer")
                .description("Informe o token JWT obtido em `POST /api/auth/login`, no formato: `Bearer {token}`");
    }
}