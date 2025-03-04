package com.example.edo.controllers;

import com.example.edo.dto.RegistrationRequest;
import com.example.edo.models.User;
import com.example.edo.services.UserService;
import com.example.edo.util.ValidationErrorMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user, Model model){
        model.addAttribute("title", "Страница регистрации");
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute RegistrationRequest registrationRequest,
                               BindingResult bindingResult, Model model, HttpServletRequest request){
        model.addAttribute("user", userService.getUserByRequest(request));
        if (bindingResult.hasErrors()) {
            model.addAllAttributes(ValidationErrorMapper.mapErrors(bindingResult));
            return "registration";
        }
        userService.createUser(registrationRequest);
        return "redirect:/login";

        //todo: обернуть в сагу
    }
}
