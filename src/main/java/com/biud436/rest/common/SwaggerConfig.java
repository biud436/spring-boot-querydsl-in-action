package com.biud436.rest.common;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("test")
                .version("1.0.0-alpha")
                .description("test description");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

}
