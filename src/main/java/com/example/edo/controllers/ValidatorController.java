package com.example.edo.controllers;

import com.example.edo.repositories.PostRepository;
import com.example.edo.services.FilesService;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequiredArgsConstructor
public class ValidatorController {

    private final UserService userService;
    private PostRepository postRepository;
    private final FilesService filesService;


    @GetMapping("/validator")
    public String validator(Model model, Principal principal){
        // скорее всего надо поменять findAll чтобы найти только определённые документы
        // findAllById мне кажется подойдёт или просто findById
//        Iterable<Post> posts = postRepository.findAll();
//        model.addAttribute("posts", posts);
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "validator";
    }

    @PostMapping("/validator")
    public ResponseEntity<Resource> downloadFiles(HttpServletRequest request, Model model) {
        if (request.getParameter("uniqueGroupCode").isEmpty()){
            HttpHeaders headers = new HttpHeaders();
//            headers.add("errorMessage", "Enter the code from letter");
            model.addAttribute("message", "Введите код из письма");
            return ResponseEntity.status(HttpStatus.FOUND).headers(headers)
                    .location(URI.create("validator")).build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<String> fileNames = searchNameFiles(request);
        List<Resource> resources = new ArrayList<>();

        try {
            for (String fileName : fileNames) {
                Path filePath = filesService.getFilePathByName(fileName);
                FileSystemResource resource = new FileSystemResource(filePath);
                resources.add(resource);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=download.zip"); // Используйте любое имя файла

            // Создаем временный файл для сохранения всех файлов в виде zip
            Path tempZipFile = java.nio.file.Files.createTempFile("files", ".zip");
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(tempZipFile.toFile()))) {
                for (Resource resource : resources) {
                    ZipEntry zipEntry = new ZipEntry(resource.getFilename());
                    zipOutputStream.putNextEntry(zipEntry);
                    StreamUtils.copy(resource.getInputStream(), zipOutputStream);
                    zipOutputStream.closeEntry();
                }
            }

            // Читаем временный файл и возвращаем его как ресурс
            ByteArrayResource zipResource = new ByteArrayResource(java.nio.file.Files.readAllBytes(tempZipFile));

            // Удаляем временный файл после использования
            Files.deleteIfExists(tempZipFile);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(zipResource);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<String> searchNameFiles(HttpServletRequest request){
        String uniqueGroupCode = request.getParameter("uniqueGroupCode");
        return filesService.findAllUniqueNamesByUniqueGroupCode(uniqueGroupCode);
    }
}
