package com.example.edo.controllers;

import com.example.edo.Models.User;
import com.example.edo.repositories.UserRepository;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class personalAccountController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/personalAccount")
    public String personalAccount(Principal principal, Model model){
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "personalAccount";
    }

    @PostMapping("/personalAccount")
    public String supplementInformation(
            @RequestParam String name,
            @RequestParam String mail,
            @RequestParam String numberGroup,
            @RequestParam String teacherMail,
            Model model){
        User user = userRepository.findByMail(mail);
        model.addAttribute("user", user);
        user.setName(name);
        user.setMail(mail);
        user.setNumberGroup(numberGroup);
        user.setTeacherMail(teacherMail);
        userRepository.saveAndFlush(user);
        return "personalAccount";
    }
}
//http://localhost:8080/personalAccount?qwe=имя