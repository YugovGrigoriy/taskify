package ru.edu.taskify.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ru.edu.taskify.entity.AppUser;
import ru.edu.taskify.repo.UserRepository;


import java.io.IOException;
import java.util.Collections;
import java.util.Optional;


/**
 * Фильтр, который проверяет наличие валидного JWT-токена
 * (считанного из cookie "token") и выставляет аутентификацию,
 * если токен ок.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {


        String jwt = getTokenFromCookie(request);

        if (jwt != null) {
            try {

                Jws<Claims> jwsClaims = jwtService.validateToken(jwt);
                Claims body = jwsClaims.getBody();


                String email = body.getSubject();


                Optional<AppUser> userOpt = userRepository.findByEmail(email);
                if (userOpt.isPresent()) {
                    AppUser appUser = userOpt.get();


                    UserDetails userDetails = org.springframework.security.core.userdetails.User
                        .withUsername(appUser.getEmail())
                        .password(appUser.getPassword())
                        .authorities(Collections.emptyList())
                        .build();


                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                logger.warn("unatorize");
            }
        }


        filterChain.doFilter(request, response);
    }

    /**
     * Вспомогательный метод, который ищет cookie с именем "token".
     * Если нет - возвращает null.
     */
    private String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("token".equals(c.getName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }
}