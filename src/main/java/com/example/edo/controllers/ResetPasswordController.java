package com.example.edo.controllers;

import com.example.edo.models.User;
import com.example.edo.repositories.UserRepository;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ResetPasswordController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/resetPassword")
    public String resetPassword(Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String supplementPassword(Model model, Principal principal, @RequestParam String password, HttpSession httpSession){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        if (password.isEmpty()){
            model.addAttribute("message", "Введите пароль");
            return "/resetPassword";
        }
        User user = userRepository.findByMail((String) httpSession.getAttribute("userMail"));
        user.setPassword(passwordEncoder.encode(password));
        userRepository.saveAndFlush(user);
        return "redirect:/login";

    }
}
