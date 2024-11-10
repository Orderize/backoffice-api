package com.orderize.backoffice_api.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite requisições em todos os endpoints
                .allowedOrigins(
                    "http://localhost:5173", 
                    "http://orderize-frontend.azurewebsites.net",
                    "https://orderize-frontend.azurewebsites.net") // Origem permitida (front-end)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
                .allowedHeaders("*"); // Todos os header permitidos
    }
}
