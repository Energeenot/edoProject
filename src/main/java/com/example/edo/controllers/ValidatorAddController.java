package com.example.edo.controllers;

import com.example.edo.models.Files;
import com.example.edo.models.Task;
import com.example.edo.models.User;
import com.example.edo.services.FilesService;
import com.example.edo.services.NotificationService;
import com.example.edo.services.TaskService;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ValidatorAddController {

    private final UserService userService;
    private final TaskService taskService;
    private final FilesService filesService;
    private final NotificationService notificationService;


    @GetMapping("/validator-add")
    public String validatorAdd(Model model, @RequestParam("taskId") String strTaskId,
                               HttpSession session, HttpServletRequest request) {
        model.addAttribute("user", userService.getUserByRequest(request));
        session.setAttribute("taskId", strTaskId);
        return "validator-add";
    }

    @PostMapping("/validator-add")
    public String uploadFile(@RequestParam("file") List<MultipartFile> files, Model model,
                             Authentication authentication, HttpServletRequest request,
                             HttpSession session) {

        if (authentication == null || authentication.getPrincipal() == null) {
            return "redirect:/login";
        }

        User currentUser = userService.getUserByRequest(request);
        model.addAttribute("user", currentUser);

        if (files.isEmpty()) {
            model.addAttribute("message", "Пожалуйста выберите файлы для отправки");
            return "validator-add";
        }

        String uniqueID = UUID.randomUUID().toString();
        Long taskId = Long.parseLong((String) session.getAttribute("taskId"));

        Task desiredTask = taskService.getTaskById(taskId);
        if (desiredTask == null) {
            model.addAttribute("message", "Произошла ошибка, id задачи не привязался, попробуйте позже");
            return "validator-add";
        }

        String uniqueFileName = filesService.saveZippedFiles(uniqueID, files);
        Files savedFiles = filesService.saveFiles(uniqueFileName, uniqueID, currentUser);
        taskService.updateTaskWithFiles(desiredTask, savedFiles);

        notificationService.notifyTeacher(desiredTask, uniqueID, request.getParameter("messageToTeacher"));

        model.addAttribute("message", "Успешная отправка файлов");
        return "validator-add";
    }
}
