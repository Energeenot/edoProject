package com.example.edo.controllers;

import com.example.edo.models.Task;
import com.example.edo.repositories.TaskRepository;
import com.example.edo.services.FilesService;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ValidatorController {

    private final UserService userService;
    private final FilesService filesService;
    private final TaskRepository taskRepository;
    private Boolean isSearchNameFilesIsEmpty = false;


    @GetMapping("/validator")
    public String validator(Model model, Principal principal) {
        // скорее всего надо поменять findAll чтобы найти только определённые документы
        // findAllById мне кажется подойдёт или просто findById
//        Iterable<Post> posts = postRepository.findAll();
//        model.addAttribute("posts", posts);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        if (isSearchNameFilesIsEmpty) {
            model.addAttribute("message", "Нет совпадений, пожалуйста проверьте корректность кода");
            isSearchNameFilesIsEmpty = false;
        }
        return "validator";
    }

    @PostMapping("/validator")
    public ResponseEntity<Resource> downloadFiles(HttpServletRequest request) {
        if (request.getParameter("uniqueGroupCode").isEmpty()){
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("validator")).build();
        }
        if (searchNameFiles(request).isEmpty()){
            isSearchNameFilesIsEmpty = true;
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create("validator")).build();
        }

        try {
            Optional<Task> currentTask = taskRepository.findByUniqueGroupCode(request.getParameter("uniqueGroupCode"));
            Task desiredTask;
            if (currentTask.isPresent()){
                desiredTask = currentTask.get();
                desiredTask.setStage("Документы на проверке");
                taskRepository.save(desiredTask);
            }
            String fileName = searchNameFiles(request);

            // Путь к существующему архиву
            Path existingZipFilePath = filesService.getFilePathByName(fileName);

            // Читаем содержимое архива
            ByteArrayResource zipResource = new ByteArrayResource(java.nio.file.Files.readAllBytes(existingZipFilePath));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=download.zip");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(zipResource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String searchNameFiles(HttpServletRequest request) {
        String uniqueGroupCode = request.getParameter("uniqueGroupCode");
        return filesService.findUniqueNameByUniqueGroupCode(uniqueGroupCode);
    }
}
