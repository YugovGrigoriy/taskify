package ru.edu.taskify.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешить запросы ко всем URL
            .allowedOrigins("http://localhost:5173") // Разрешить только с этого адреса (React)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешённые HTTP-методы
            .allowedHeaders("*") // Разрешить любые заголовки
            .allowCredentials(true); // Разрешить отправку cookies
    }
}
