package com.balmis.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    
    // http://localhost:8080/proyecto
    // http://localhost:8080/proyecto/swagger-ui.html
    // http://localhost:8080/proyecto/api-docs
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Heladeria")
                        .version("1.0.0")
                        .description("API REST para gestión de base de datos de una heladeria. Proyecto DWES UD5 de Vadim Elshin")
                        .contact(new Contact()
                                .name("Balmis")
                                .email("admin@balmis.com")
                                .url("https://www.bamis.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .termsOfService("https://www.balmis.com/terminos"));
        

    }
}