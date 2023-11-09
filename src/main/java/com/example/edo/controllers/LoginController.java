package com.example.edo.controllers;

import com.example.edo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

//    @GetMapping("/login")
//    public String login(Model model, Principal principal, @RequestParam(name = "error", required = false) String error, @RequestParam(name = "logout", required = false) String logout){
//        model.addAttribute("user", userService.getUserByPrincipal(principal));
//        if (error != null){
//            model.addAttribute("errorMessage", "Нкверный логин или пароль");
//        } else if (logout != null) {
//            model.addAttribute("errorMessage", "Успешный вход");
////            return "redirect:/personalAccount";
//        }
//        return "login";
//    }

    @GetMapping("/login")
    public String login(Model model, Principal principal, @RequestParam(name = "error", required = false) String error, @RequestParam(name = "logout", required = false) String logout) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        if (error != null) {
            model.addAttribute("errorMessage", "Неверный логин или пароль");
        }
        else if(logout != null){
            model.addAttribute("errorMessage", "Успешный выход");
        }
        return "login";
    }
}
