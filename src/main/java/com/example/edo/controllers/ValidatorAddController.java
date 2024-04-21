package com.example.edo.controllers;

import com.example.edo.mailSender.Sender;
import com.example.edo.models.Task;
import com.example.edo.models.User;
import com.example.edo.repositories.FilesRepository;
import com.example.edo.repositories.TaskRepository;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequiredArgsConstructor
public class ValidatorAddController {
    private final UserService userService;

    private static String UPLOAD_FOLDER = "src/main/webapp/uploads/";
    @Autowired
    private FilesRepository filesRepository;
    Sender sender = new Sender();
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private final TaskRepository taskRepository;


    @GetMapping("/validator-add")
    public String validatorAdd(Model model, Principal principal, @RequestParam("taskId") String strTaskId, HttpSession session){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        session.setAttribute("taskId", strTaskId);
        return "validator-add";
    }

    @PostMapping("/validator-add")
    public String uploadFile(@RequestParam("file") List<MultipartFile> files, Model model, Authentication authentication, HttpServletRequest request, Principal principal, HttpSession session) throws IOException {
        model.addAttribute(userService.getUserByPrincipal(principal));
        String uniqueID = UUID.randomUUID().toString();

        Long taskId = Long.parseLong((String) session.getAttribute("taskId"));
        Optional<Task>  task = taskRepository.findTaskById(taskId);
        Task desiredTask;

        if (files.isEmpty()) {
            model.addAttribute("message", "Пожалуйста выберите файлы для отправки");
            return "validator-add";
        }
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(uniqueID.getBytes(StandardCharsets.UTF_8));
            String uniqueFileName = Base64.getEncoder().encodeToString(hash) + "_" + uniqueID;
            uniqueFileName = uniqueFileName.replaceAll("/", "_");
            uniqueFileName = uniqueFileName + ".zip";

            saveZipArchive(files, uniqueFileName);

            if (authentication != null && authentication.getPrincipal() != null) {
                com.example.edo.models.Files newFiles = new com.example.edo.models.Files();
                newFiles.setUniqueName(uniqueFileName);
                newFiles.setUniqueGroupCode(uniqueID);
                User currentUser = (User) authentication.getPrincipal();
                newFiles.setSender(currentUser);
                filesRepository.save(newFiles);
                model.addAttribute("message", "Успешная отправка файлов");
                if (task.isPresent()){
                    desiredTask = task.get();
                    desiredTask.setFiles(newFiles);
                    desiredTask.setStage("Документы отправлены");
                    taskRepository.save(desiredTask);
                }else {
                    model.addAttribute("message", "Произошла ошибка, id задачи не привязался, попробуйте позже");
                    return "validator-add";
                }
            } else {
                // Обработка ситуации, когда Authentication или Principal равны null
                return "redirect:/login";
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String messageToTeacher = request.getParameter("messageToTeacher");
        String teacherMail = desiredTask.getSender().getMail();
        String fio = desiredTask.getUser().getName();
        String numberGroup = desiredTask.getUser().getNumberGroup();
        if (!EMAIL_PATTERN.matcher(teacherMail).matches()){
            model.addAttribute("message", "Почта преподавателя не указана");
            return "validator-add";
        } else {
            sender.sendNotificationOfNewDocuments(uniqueID, teacherMail, fio, numberGroup, messageToTeacher);
        }


//        for (MultipartFile file : files){
//            try {
//                byte[] bytes = file.getBytes();
//
//            String originalFilename = file.getOriginalFilename();
//            String extension = FilenameUtils.getExtension(originalFilename);
//
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//                assert originalFilename != null;
//                byte[] hash = digest.digest(originalFilename.getBytes(StandardCharsets.UTF_8));
//            String uniqueFileName = Base64.getEncoder().encodeToString(hash) + "_" + uniqueID;
//            uniqueFileName = uniqueFileName.replaceAll("/", "_");
//            uniqueFileName = uniqueFileName + "." + extension;
//
//            Path path = Paths.get(UPLOAD_FOLDER);
//            Path filePath = path.resolve(uniqueFileName);
//            Files.write(filePath, bytes);
//            model.addAttribute("message", "Успешная отправка файлов");
//
//                // Создание папки пользователя (вместо UPLOAD_FOLDER может быть другой путь)
////                Path userDirectory = Paths.get(UPLOAD_FOLDER + "user_" + uniqueID);
////                if (!Files.exists(userDirectory)) {
////                    Files.createDirectory(userDirectory);
////                }
////                проверка на аутентификацию
//            if (authentication != null && authentication.getPrincipal() != null) {
//                com.example.edo.models.Files newFiles = new com.example.edo.models.Files();
//                newFiles.setUniqueName(uniqueFileName);
//                newFiles.setUniqueGroupCode(uniqueID);
//                User currentUser = (User) authentication.getPrincipal();
//                newFiles.setSender(currentUser);
//                filesRepository.save(newFiles);
//                if (task.isPresent()){
//                    desiredTask = task.get();
//                    desiredTask.setFiles(newFiles);
//                    desiredTask.setStage("Документы отправлены");
//                    taskRepository.save(desiredTask);
//                }else {
//                    model.addAttribute("message", "Произошла ошибка, id задачи не привязался, попробуйте позже");
//                    return "validator-add";
//                }
//            } else {
//                // Обработка ситуации, когда Authentication или Principal равны null
//                return "redirect:/login";
//            }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                model.addAttribute("message", "Ошибка при отправке файлов");
//            } catch (NoSuchAlgorithmException e) {
//                throw new RuntimeException(e);
//            }
//        }

//        String messageToTeacher = request.getParameter("messageToTeacher");
        // через сессию передать преподу сообщение не получится, так как у него будет другая сессия, нужно скорее всего бд

        //todo: выключить впн перед отправкой письма

//        assert desiredTask != null;
//        String teacherMail = desiredTask.getSender().getMail();
//        if (!EMAIL_PATTERN.matcher(teacherMail).matches()){
//            model.addAttribute("message", "Укажите почту преподавателя");
//            return "validator-add";
//        } else {
//            sender.sendNotificationOfNewDocuments(uniqueID, teacherMail);
//        }
        return "validator-add";
    }

    public  void saveZipArchive(List<MultipartFile> files, String uniqueFileName) throws IOException {
//        String uniqueID = UUID.randomUUID().toString();
        String zipFileName = UPLOAD_FOLDER + uniqueFileName;

        try (FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String extension = FilenameUtils.getExtension(originalFilename);

                // Создаем имя файла в архиве, включая его оригинальное расширение
                String zipEntryName = originalFilename + "." + extension;

                // Создаем новую запись для файла в архиве
                zos.putNextEntry(new ZipEntry(zipEntryName));

                // Записываем содержимое файла в архив
                zos.write(file.getBytes());
                zos.closeEntry();
            }
        }
    }
    
}
