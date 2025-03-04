package com.example.edo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotBlank(message = "ФИО не может быть пустым")
    private String fullName;

    @Email(message = "Некорректный email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    private String password;
}
