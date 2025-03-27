package com.example.edo.controllers;

import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final UserService userService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("user", userService.getUserByRequest(request));
        return "home";
    }
}
