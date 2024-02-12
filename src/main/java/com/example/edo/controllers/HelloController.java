package com.example.edo.controllers;

import com.example.edo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HelloController {
    private final UserService userService;
    @GetMapping("/")
//    @RequestMapping("/") //при переходе на страницу выполняется метод
    //@RequestParam(value="name", required=false, defaultValue="World") String name,
    public String home(Model model, Principal principal) {
//        model.addAttribute("title", "Главная страница");
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "home";
    }
}
