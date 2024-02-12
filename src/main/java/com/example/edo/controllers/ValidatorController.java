package com.example.edo.controllers;

import com.example.edo.models.Files;
import com.example.edo.repositories.PostRepository;
import com.example.edo.services.FilesService;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    // работает на загркзку одного файла, но когда больше выкидывает ошибку ERR_RESPONSE_HEADERS_MULTIPLE_CONTENT_DISPOSITION
    @PostMapping("/validator")
    public ResponseEntity<Resource> downloadFiles(HttpServletRequest request) {
        List<String> fileNames = searchNameFiles(request);
        List<Resource> resources = new ArrayList<>();

        try {
            for (String fileName : fileNames) {
                Path filePath = filesService.getFilePathByName(fileName);
                FileSystemResource resource = new FileSystemResource(filePath);
                resources.add(resource);
            }

            HttpHeaders headers = new HttpHeaders();
            for (String fileName : fileNames) {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
            }
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resources.get(0)); // Тут выбираем первый ресурс из списка (можно подстроить под вашу логику)
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


//        List<Resource> resources = new ArrayList<>();
//        List<String> fileNames = searchNameFiles(request);
//        FileSystemResource resource = null;
//        for (String fileName : fileNames) {
//            Path filePath = filesService.getFilePathByName(fileName);
//            resource = new FileSystemResource(filePath);
//            resources.add(resource);
//        }
////        FileSystemResource resource = new FileSystemResource(filesService.getFilePathByName(fileNames));
//
//        assert resource != null;
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileNames)
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .contentLength(resource.getFile().length())
//                .body(resource);
    }

    public List<String> searchNameFiles(HttpServletRequest request){
        String uniqueGroupCode = request.getParameter("uniqueGroupCode");
        return filesService.findAllUniqueNamesByUniqueGroupCode(uniqueGroupCode);
    }
}
