package com.example.edo.feign;

import com.example.edo.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            ErrorResponse errorResponse = mapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            log.error("Feign error: method={} status={} body={}", methodKey, response.status(), response.body());

            return switch (response.status()){
                case 401 -> new RuntimeException("Ошибка авторизации" + errorResponse.getMessage());
                case 404 -> new RuntimeException("Ошибка " + errorResponse.getMessage());
                case 409 -> new RuntimeException("Конфликт " + errorResponse.getMessage());
                default -> new RuntimeException("Неизвестная ошибка " + errorResponse.getMessage());
            };
        } catch (IOException e) {
            log.error("Ошибка обработки feign {}", e.getMessage());
            return new RuntimeException("Ошибка обработки feign");
        }
    }
}
// todo: добавить обработку 422 кода .... возможно не надо потому что спринг секурити перехватывает запрос
//  и я вообще удалю обработку данной ошибки