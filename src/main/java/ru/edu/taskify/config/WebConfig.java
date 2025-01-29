package ru.edu.taskify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /uploads/** -> file:uploads/
        registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:C:/Users/grish/IdeaProjects/pet-projects/Taskify/taskify/uploads/");
    }


}