package com.banquito.core.cuentas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Cuentas Configuración")
                        .version("v1")
                        .description("Documentación de los endpoints del catalogo de cuentas, servicios y comisiones/cargos"));
    }
}