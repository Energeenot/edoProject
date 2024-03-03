package com.example.edo.controllers;

import com.example.edo.models.User;
import com.example.edo.repositories.UserRepository;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CheckCodeController {
    private final UserService userService;
    private final UserRepository userRepository;
    @GetMapping("/checkCode")
    public String checkCode(Model model, Principal principal){
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "/checkCode";
    }

    @PostMapping("/checkCode")
    public String check(Model model, @RequestParam String code, HttpSession httpSession){
        /*
        каждый пользователь обычно имеет свой собственный уникальный объект HttpSession.
        Когда пользователь взаимодействует с веб-приложением, сервер создает новый объект HttpSession и присваивает ему уникальный идентификатор,
         который обычно называется идентификатором сеанса (session ID).
         */
//        из-за того что в модел добавляю юзера с известной почтой ломается хедер, попробовать передавать через принципал
        User user = userRepository.findByMail((String) httpSession.getAttribute("userMail"));
        model.addAttribute("user", user);
        if (code == null || code.isEmpty()){
            model.addAttribute("message", "Введите код");
            return "checkCode";
        }
        if (httpSession.getAttribute("token") == null){
            model.addAttribute("message", "Что-то пошло не так, к пользователю не привязался код");
            return "checkCode";
        }
        if (!code.equals(httpSession.getAttribute("token"))){
            model.addAttribute("message", "Код не сошёлся, перепроверьте введёный код");
            return "checkCode";
        }else {
            return "redirect:/resetPassword";
        }
    }
}
