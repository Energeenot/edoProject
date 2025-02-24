package com.example.edo.services;

import com.example.edo.dto.AuthRequest;
import com.example.edo.dto.TokenResponse;
import com.example.edo.feign.AuthFeignClient;
import com.example.edo.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthClientService {

    private final AuthFeignClient authFeignClient;

    public TokenResponse authenticate(String email, String password) {
        AuthRequest request = new AuthRequest(email, password);
        try {
            log.info("Попытка аутентифицироваться через feign");
            return authFeignClient.authenticate(request);
        } catch (HttpClientErrorException e) {
            log.error("Ошибка аутентификации {}", e.getResponseBodyAsString());
            throw new RuntimeException("Ошибка аутентификации: " + e.getResponseBodyAsString());
        }
    }

    public TokenResponse refreshToken(String refreshToken) {
        try {
            log.info("Попытка обновления токенов");
            return authFeignClient.refreshToken(refreshToken);
        } catch (HttpClientErrorException e) {
            log.error("Ошибка обновления токена: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Ошибка обновления токена: " + e.getResponseBodyAsString());
        }

    }

    public String registration(User user) {
        AuthRequest request = new AuthRequest(user.getMail(), user.getPassword());
        try {
            log.info("Попытка регистрации пользователя с почтой: {}", user.getMail());
            return authFeignClient.registration(request);
        } catch (Exception e) {
            log.error("Ошибка регистрации: {} для пользователя с почтой {}", e.getMessage(), user.getMail(), e);
            throw new RuntimeException("Ошибка регистрации: " + e.getMessage());
        }
    }
}
