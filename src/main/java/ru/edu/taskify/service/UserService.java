package ru.edu.taskify.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.taskify.config.JwtConfig;
import ru.edu.taskify.entity.User;
import ru.edu.taskify.repo.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UserService {

    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    @Autowired
    UserService(JwtConfig jwtConfig, UserRepository userRepository) {
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<Long> getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
            return Optional.of(Long.valueOf(claims.getSubject()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    private String getJWTSecret(){
        return jwtConfig.getSecret();
    }
}
