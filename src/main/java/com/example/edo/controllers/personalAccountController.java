package com.example.edo.controllers;

import com.example.edo.Models.User;
import com.example.edo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class personalAccountController {
    private final UserService userService;

    @GetMapping("/personalAccount")
    public String personalAccount(Principal principal, Model model){
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "personalAccount";
    }
}
