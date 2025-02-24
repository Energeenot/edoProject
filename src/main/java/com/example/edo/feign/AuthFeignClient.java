package com.example.edo.feign;

import com.example.edo.configurations.FeignConfig;
import com.example.edo.dto.AuthRequest;
import com.example.edo.dto.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "${auth.service.url.base}", configuration = FeignConfig.class)
public interface AuthFeignClient {

    @PostMapping(value = "${auth.service.url.login}")
    TokenResponse authenticate(@RequestBody AuthRequest authRequest);

    @PostMapping(value = "${auth.service.url.refresh}")
    TokenResponse refreshToken(@RequestParam("refreshToken") String refreshToken);

    @PostMapping(value = "${auth.service.url.registration}")
    String registration(@RequestBody AuthRequest authRequest);
}
