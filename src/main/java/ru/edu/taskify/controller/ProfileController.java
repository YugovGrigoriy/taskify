package ru.edu.taskify.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.taskify.entity.User;
import ru.edu.taskify.service.UserService;
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(PlaceController.class);
    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        // Получаем токен из cookies
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null) {
            Long userId = userService.getUserIdFromToken(token)
                .orElse(null);

            if (userId != null) {

                User user = userService.getUserById(userId).orElse(null);
                if (user != null) {
                    return ResponseEntity.ok(user);  // Возвращаем данные пользователя
                }
            }
        }
        return ResponseEntity.status(401).body("Unauthorized");  // Если токен отсутствует или недействителен
    }
}
