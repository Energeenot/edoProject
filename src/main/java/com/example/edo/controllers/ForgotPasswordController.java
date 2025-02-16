package com.example.edo.controllers;

import com.example.edo.dto.MessageDto;
import com.example.edo.kafka.SenderProducer;
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
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final SenderProducer producer;

    @GetMapping("/forgotPassword")
    public String forgotPassword(Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String resetPassword(@RequestParam String mail, Model model, Principal principal, HttpSession httpSession){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        if (mail == null || mail.isEmpty()){
            model.addAttribute("message", "Введите почту");
            return "/forgotPassword";
        }
        User user = userRepository.findByMail(mail);
        if (user == null){
            model.addAttribute("message", "Пользователь с такой почтой не найден.");
            return "/forgotPassword";
        }
        String token = UUID.randomUUID().toString();
//        надо ли записывать в бд этот токен, создать для него таблицу с ссылкой на юзера

        producer.sendNotificationOfResetPassword(MessageDto.builder()
                .token(token)
                .toEmail(mail)
                .build());

        httpSession.setAttribute("userMail", mail);
        httpSession.setAttribute("token", token);
        return "redirect:/checkCode";
    }
}
