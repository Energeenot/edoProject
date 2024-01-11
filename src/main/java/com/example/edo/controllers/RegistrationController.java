package com.example.edo.controllers;

import com.example.edo.models.User;
import com.example.edo.repositories.UserRepository;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("title", "Страница регистрации");
        return "registration";

    }

    @PostMapping("/registration")
    public String createUser(User user, Model model, HttpSession session){
        model.addAttribute("user", user);
        String message = userService.createUser(user);
        if (message.equals("true")){
            session.setAttribute("successMessage", "Успешная регистрация");
            return "redirect:/login";
        }else {
            model.addAttribute("errorMessage", "Аккаунт с такой почтой уже есть");
            return "registration";
        }

    }
}
