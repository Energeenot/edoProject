package com.example.edo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    @Email(message = "Неверный формат электронной почты")
    @NotBlank(message = "Требуется ввести электронную почту")
    private String email;

    @NotBlank(message = "Требуется ввести пароль")
    @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
    private String password;
}
