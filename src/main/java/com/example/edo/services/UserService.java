package com.example.edo.services;

import com.example.edo.Models.User;
import com.example.edo.Models.enums.Role;
import com.example.edo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String createUser(User user){
        String message = "true";
        if (userRepository.findByMail(user.getMail()) != null){
            message = "false mail";

        } else {
            String mail = user.getMail();
            System.out.println(mail);
            System.out.println(user.getName());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(passwordEncoder.encode(user.getPassword()));
            user.getRoles().add(Role.ROLE_student);
            log.info("Saving new User with email: {}", mail);
            userRepository.save(user);
        }
        return message;
    }

    public User getUserByPrincipal(Principal principal){
        if (principal == null) return new User();
        return userRepository.findByMail(principal.getName());
    }

}
