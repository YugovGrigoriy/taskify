package ru.edu.taskify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.edu.taskify.config.JwtConfig;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class TaskifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskifyApplication.class, args);

    }

}
