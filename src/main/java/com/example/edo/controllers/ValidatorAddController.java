package com.example.edo.controllers;

import com.example.edo.mailSender.Sender;
import com.example.edo.models.User;
import com.example.edo.repositories.FilesRepository;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class ValidatorAddController {
    private final UserService userService;

    private static String UPLOAD_FOLDER = "src/main/webapp/uploads/";
    @Autowired
    private FilesRepository filesRepository;
    Sender sender = new Sender();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");


    @GetMapping("/validator-add")
    public String validatorAdd(Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        return "validator-add";
    }

    @PostMapping("/validator-add")
    public String uploadFile(@RequestParam("file") List<MultipartFile> files, Model model, Authentication authentication, HttpServletRequest request, Principal principal) {
        model.addAttribute(userService.getUserByPrincipal(principal));
        String uniqueID = UUID.randomUUID().toString();


        if (files.isEmpty()) {
            model.addAttribute("message", "Пожалуйста выберите файлы для отправки");
            return "validator-add";
        }
        for (MultipartFile file : files){
            try {
                byte[] bytes = file.getBytes();
//                Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
//                Files.write(path, bytes);

              /*
               записывается файл в папку upload с уникальным именем,
                либо создавать папки с именем пользователя и запихивать
               туда все файлы с их именем, а в бд записывать и имя и уникальный код
               */

            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
//            String filenameWithoutExtension = FilenameUtils.removeExtension(originalFilename);


            MessageDigest digest = MessageDigest.getInstance("SHA-256");
                assert originalFilename != null;
                byte[] hash = digest.digest(originalFilename.getBytes(StandardCharsets.UTF_8));
            String uniqueFileName = Base64.getEncoder().encodeToString(hash) + "_" + uniqueID;
            uniqueFileName = uniqueFileName.replaceAll("/", "_");
            uniqueFileName = uniqueFileName + "." + extension;

            Path path = Paths.get(UPLOAD_FOLDER);
            Path filePath = path.resolve(uniqueFileName);
            Files.write(filePath, bytes);
            model.addAttribute("message", "Успешная отправка файлов");


                // Создание папки пользователя (вместо UPLOAD_FOLDER может быть другой путь)
//                Path userDirectory = Paths.get(UPLOAD_FOLDER + "user_" + uniqueID);
//                if (!Files.exists(userDirectory)) {
//                    Files.createDirectory(userDirectory);
//                }
//                проверка на аутентификацию
            if (authentication != null && authentication.getPrincipal() != null) {
                com.example.edo.models.Files newFiles = new com.example.edo.models.Files();
                newFiles.setUniqueName(uniqueFileName);
                newFiles.setUniqueGroupCode(uniqueID);
                User currentUser = (User) authentication.getPrincipal();
                newFiles.setSender(currentUser);
                filesRepository.save(newFiles);
                
            } else {
                // Обработка ситуации, когда Authentication или Principal равны null
                return "redirect:/login";
            }

            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "Ошибка при отправке файлов");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }

        //todo: выключить впн перед отправкой письма
        String teacherMail = request.getParameter("teacherMail");
        if (!EMAIL_PATTERN.matcher(teacherMail).matches()){
            model.addAttribute("message", "Укажите почту преподавателя");
            return "validator-add";
        } else {
            sender.sendNotificationOfNewDocuments(uniqueID, teacherMail);
        }
//        if (teacherMail != null){
//            sender.sendNotificationOfNewDocuments(uniqueID, teacherMail);
//        }

//        groupNameCode = stringBuilder.toString();

        return "validator-add";
    }
    
}
