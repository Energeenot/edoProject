package com.example.edo.controllers;

import com.example.edo.models.Task;
import com.example.edo.models.User;
import com.example.edo.services.NotificationService;
import com.example.edo.services.TaskService;
import com.example.edo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PersonalAccountController {
    private final UserService userService;
    private final TaskService taskService;
    private final NotificationService notificationService;

    @GetMapping("/personalAccount")
    public String personalAccount(HttpServletRequest request,
                                  Model model, Principal principal){
        log.info("GET /personalAccount вызван, principal = {}", principal.getName());
        User user = userService.getUserByRequest(request);
        model.addAttribute("user", user);
        model.addAttribute("email", principal.getName());

        if (user.isTeacher()){
            List<String> numberGroups = userService.getNumberGroups();
            if (!numberGroups.isEmpty()){
                model.addAttribute("numberGroups", numberGroups);
            }
            List<Task> allTasks = taskService.getAllTasks();
            if (!allTasks.isEmpty()){
                model.addAttribute("tasks", allTasks);
                System.out.println(allTasks);
            }
        }else{
            model.addAttribute("tasks", taskService.getAllTasksByUserId(user.getId()));
        }
        return "personalAccount";
    }

    @PostMapping("/personalAccount/createTask")
    public String createTask(@RequestParam List<String> selectedGroups, @RequestParam String message,
                             @RequestParam String deadline,
                             Model model,  HttpServletRequest request, Principal principal){
        log.info("Post /personalAccount/createTask вызван, principal = {}", principal.getName());
        User user = userService.getUserByRequest(request);
        model.addAttribute("user", user);
        model.addAttribute("numberGroups", userService.getNumberGroups());
        taskService.createTask(selectedGroups, message, deadline, user);
        model.addAttribute("tasks", taskService.getAllTasks());
        return "redirect:/personalAccount";
    }

    @PostMapping("/personalAccount/execute")
    public String executeTask(@RequestParam String executeTaskId, Model model,
                              Principal principal, HttpServletRequest request){
        log.info("Post /personalAccount/execute вызван, principal = {}", principal.getName());
        model.addAttribute("user", userService.getUserByRequest(request));
        taskService.executeTask(executeTaskId);
        return "redirect:/personalAccount";
    }

    @PostMapping("/personalAccount/delete")
    public String deleteTask(Model model, Principal principal,
                             HttpServletRequest request, @RequestParam String deleteTaskId){
        log.info("Post /personalAccount/delete вызван, principal = {}", principal.getName());
        model.addAttribute("user", userService.getUserByRequest(request));
        taskService.deleteTask(deleteTaskId);
        return "redirect:/personalAccount";
    }

    @PostMapping("/personalAccount/feedback")
    public String feedbackToStudent(Model model, Principal principal, @RequestParam String feedbackTaskId,
                                    @RequestParam String feedbackInput, HttpServletRequest request){
        log.info("Post /personalAccount/feedback вызван, principal = {}", principal.getName());
        model.addAttribute("user", userService.getUserByRequest(request));

        Task desiredTask = taskService.getTaskById(Long.parseLong(feedbackTaskId));
        taskService.updateTaskStage(desiredTask, "Отправлены правки");
        notificationService.sendFeedbackToStudent(feedbackInput, desiredTask);
        return "redirect:/personalAccount";
    }
}
//http://localhost:8080/personalAccount?qwe=имя
// todo: разобраться с временем истечения токена и его обновлением