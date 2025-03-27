package com.example.edo.controllers;

import com.example.edo.dto.TokenResponse;
import com.example.edo.models.User;
import com.example.edo.services.AuthClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AuthClientService authClientService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpServletResponse response, HttpSession session,
                        @ModelAttribute("user") User user, Model model) {
        try {
            TokenResponse tokenResponse = authClientService.authenticate(username, password);
            System.out.println(tokenResponse);

            Cookie cookie = new Cookie("JWT", tokenResponse.getAccessToken());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            session.setAttribute("refreshToken", tokenResponse.getRefreshToken());

            return "redirect:/personalAccount";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user,
                        @RequestParam(name = "error", required = false) String error,
                        @RequestParam(name = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверный логин или пароль");
        } else if (logout != null) {
            model.addAttribute("errorMessage", "Успешный выход");
        }

        return "login";
    }

}
