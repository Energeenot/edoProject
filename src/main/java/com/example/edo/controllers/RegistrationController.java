package com.example.edo.controllers;

import com.example.edo.models.User;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model, Principal principal){
        model.addAttribute("title", "Страница регистрации");
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "registration";

    }

    @PostMapping("/registration")
    public String createUser(@Valid User user, BindingResult bindingResult, Model model, HttpSession session){
        model.addAttribute("user", user);
        if (bindingResult.hasErrors()){
            Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                    fieldError -> fieldError.getField() + "Error",
                    FieldError::getDefaultMessage
            );
            Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
            model.mergeAttributes(errorsMap);
            return "registration";
        } else {
            String message = userService.createUser(user);
            if (message.equals("true")){
                session.setAttribute("successMessage", "Успешная регистрация");
                return "redirect:/login";
            }else {
                model.addAttribute("errorMessage", "Аккаунт с такой почтой уже есть");
                model.addAttribute("bindingResult", bindingResult);
                return "registration";
            }
        }
    }
}
