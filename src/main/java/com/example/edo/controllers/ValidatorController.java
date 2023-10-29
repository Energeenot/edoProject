package com.example.edo.controllers;

import com.example.edo.Models.Post;
import com.example.edo.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ValidatorController {
    @Autowired
    private PostRepository postRepository;
    @GetMapping("/validator")
    public String validator(Model model){
        // скорее всего надо поменять findAll чтобы найти только определённые документы
        // findAllById мне кажется подойдёт или просто findById
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "validator";
    }

    @GetMapping("/validator/add")
    public String validatorAdd(Model model){
        return "validator-add";
    }
    @PostMapping("/validator/add")
    public String validatorPostAdd(@RequestParam String title, @RequestParam String isChanged, Model model){
        Post post = new Post(title, isChanged);
        postRepository.save(post);
        return "redirect:/validator";
    }
}
