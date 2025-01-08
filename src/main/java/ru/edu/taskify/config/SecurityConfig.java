package ru.edu.taskify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                //todo включить после тестов
                //.requestMatchers("/*").permitAll() // Настройка разрешений для эндпоинтов
                //.anyRequest().authenticated()
                .anyRequest().permitAll()
            )
            .cors(cors -> cors.configurationSource(corsConfigurationSource)); // Настройка CORS
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:5173"); // Разрешаем запросы с React-приложения
        configuration.addAllowedMethod("*"); // Разрешаем все методы (GET, POST и т.д.)
        configuration.addAllowedHeader("*"); // Разрешаем любые заголовки
        configuration.setAllowCredentials(true); // Разрешаем использование credentials (cookies, токены)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
