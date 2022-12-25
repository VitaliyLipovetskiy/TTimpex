package com.lvv.ttimpex2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("TTimpex").version("2.0").description("description"))
                .addSecurityItem(new SecurityRequirement().addList("my security"))
                .components(new Components().addSecuritySchemes("Token",
                        new SecurityScheme().name("Token").type(SecurityScheme.Type.HTTP).scheme("bearer")));
    }
}
