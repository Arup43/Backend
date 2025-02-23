package com.explore.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${server.port}")
    private String serverPort;

    @Value("${backend-service.url}")
    private String serverUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server()
            .url(serverUrl + ":" + serverPort)
            .description("Development server");

        Contact contact = new Contact()
            .name("API Support")
            .email("support@example.com");

        License mitLicense = new License()
            .name("MIT License")
            .url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
            .title("Device Management API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage devices.")
            .license(mitLicense);

        return new OpenAPI()
            .info(info)
            .servers(List.of(devServer));
    }
}
