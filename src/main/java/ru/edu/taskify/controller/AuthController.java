package ru.edu.taskify.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.edu.taskify.config.JwtService;
import ru.edu.taskify.entity.AppUser;
import ru.edu.taskify.repo.UserRepository;


import java.util.Optional;

@Log
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Data
    public static class RegisterRequest {
        private String name;
        private String email;
        private String password;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.info("FAILED: User already exists, email: " + request.getEmail());
            return ResponseEntity
                .status(422)
                .body("User with this email already exists");
        }


        String hashedPass = passwordEncoder.encode(request.getPassword());

        AppUser newAppUser = new AppUser();
        newAppUser.setName(request.getName());
        newAppUser.setEmail(request.getEmail());
        newAppUser.setPassword(hashedPass);


        userRepository.save(newAppUser);
        log.info("User registered successfully: " + newAppUser.getEmail());
        return ResponseEntity.ok("User registered successfully");
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestBody LoginRequest request,
        HttpServletResponse response
    ) {
        Optional<AppUser> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            log.info("User not found");
            return ResponseEntity.status(404).body("User not found");
        }

        AppUser appUser = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), appUser.getPassword())) {
            return ResponseEntity.status(422).body("Invalid password");
        }


        String token = jwtService.generateToken(appUser.getId(), appUser.getEmail());


        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);


        appUser.setPassword(null);
        return ResponseEntity.ok(appUser);
    }

    @GetMapping("/login")
    public ResponseEntity<?> handleLogout(
        @RequestParam(required = false) String logout,
        HttpServletResponse response) {

        if (logout != null) {

            Cookie cookie = new Cookie("token", null);
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return ResponseEntity.ok("Logged out");
        }

        return ResponseEntity.ok("This is login page");
    }
}
