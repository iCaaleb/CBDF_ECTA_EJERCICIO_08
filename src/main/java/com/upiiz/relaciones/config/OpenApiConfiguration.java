package com.upiiz.relaciones.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de clientes",
                description = "Esta API proporciona acceso a los recursos de la API de la tienda la Moderna",
                version = "1.0.0",
                contact = @Contact(
                        name = "Caleb Trejo",
                        email = "etrejoa1801@alumno.ipn.mx"
                )
        )
)
public class OpenApiConfiguration {
}
