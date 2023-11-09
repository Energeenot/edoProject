package com.example.edo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/")
//    @RequestMapping("/") //при переходе на страницу выполняется метод
    //@RequestParam(value="name", required=false, defaultValue="World") String name,
    public String home( Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
}
