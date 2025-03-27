package com.ecommer.usuario.document;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDoc {

    /**
     * Configura a documentação da API utilizando o SpringDoc e OpenAPI.
     * Define esquemas de segurança para autenticação via JWT e informações gerais da API.
     *
     * @return Instância de OpenAPI configurada.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key", // Nome do esquema de segurança
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP) // Define o tipo como HTTP
                                        .scheme("bearer") // Utiliza o esquema Bearer Token
                                        .bearerFormat("JWT"))) // Formato do token JWT
                // http://localhost:8087/usuario/swagger-ui/swagger-ui/index.html
                .info(new Info()
                        .title("Microsserviço de usuario da aplicação de e-commerce") // Título da API
                        .description("API para gerenciamento de usuarios em aplicação de E-commer.") // Descrição da API
                        .contact(new Contact()
                                .name("Time Backend") // Nome da equipe responsável
                                .email("backend@plataforma-ead.com")) // E-mail de contato
                        .license(new License()
                                .name("Apache 2.0") // Nome da licença
                                .url("http://plataforma-ead.com/api/licenca"))); // URL da licença
    }
}