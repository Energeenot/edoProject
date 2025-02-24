package com.example.edo.util;

import com.example.edo.dto.TokenResponse;
import com.example.edo.services.AuthClientService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AuthClientService authClientService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = extractToken(request);

        if (accessToken != null) {
            if (jwtProvider.isTokenExpired(accessToken)) {
                log.info("JWT expired, trying to refresh...");

                String refreshToken = extractRefreshToken(request);
                if (refreshToken != null) {
                    try {
                        TokenResponse tokenResponse = authClientService.refreshToken(refreshToken);

                        addCookie(response, "JWT", tokenResponse.getAccessToken());
                        addCookie(response, "REFRESH_TOKEN", tokenResponse.getRefreshToken());

                        accessToken = tokenResponse.getAccessToken();

                        log.info("JWT успешно обновлён");
                    } catch (Exception e) {
                        log.error("Ошибка при обновлении JWT, выход пользователя из системы," +
                                " сообщение об исключении: {}", e.getMessage());
                        response.sendRedirect("/login?error=expired");
                        return;
                    }
                } else {
                    log.warn("Нет Refresh Token, перенаправляем на логин");
                    response.sendRedirect("/login?error=expired");
                    return;
                }
            }

            String email = jwtProvider.getEmailFromToken(accessToken);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, List.of(
                            new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        return getCookieValue(request, "JWT");
    }

    private String extractRefreshToken(HttpServletRequest request) {
        return getCookieValue(request, "REFRESH_TOKEN");
    }

    private String getCookieValue(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void addCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
