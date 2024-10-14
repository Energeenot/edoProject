package com.example.edo.controllers;

import com.example.edo.mailSender.Sender;
import com.example.edo.models.Task;
import com.example.edo.models.User;
import com.example.edo.repositories.TaskRepository;
import com.example.edo.repositories.UserRepository;
import com.example.edo.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PersonalAccountController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @GetMapping("/personalAccount")
    public String personalAccount(Principal principal, Model model){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        if (userService.getUserByPrincipal(principal).isTeacher()){
            List<String> numberGroups = userRepository.findAllNumberGroup();
            if (!numberGroups.isEmpty()){
                System.out.println("-----------List: " + numberGroups);
                model.addAttribute("numberGroups", numberGroups);
            }
            List<Task> allTasks = taskRepository.findAll();
            if (!allTasks.isEmpty()){
                model.addAttribute("tasks", allTasks);
                System.out.println(allTasks);
            }
        }else{
            model.addAttribute("tasks", taskRepository.findAllByUserId(userService.getUserByPrincipal(principal).getId()));
            System.out.println(taskRepository.findAllByUserId(userService.getUserByPrincipal(principal).getId()));
        }
        return "personalAccount";
    }

    @PostMapping("/personalAccount/supplementInformation")
    public String supplementInformation(
            @RequestParam String name,
            @RequestParam String mail,
            @RequestParam String numberGroup,
            Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        if (numberGroup.isEmpty()){
            model.addAttribute("message", "Введите данные");
            return "personalAccount";
        }
        User user = userRepository.findByMail(mail);
        model.addAttribute("user", user);
        user.setName(name);
        user.setMail(mail);
        user.setNumberGroup(numberGroup);
        userRepository.saveAndFlush(user);
        model.addAttribute("message", "Данные успешно обновлены");
        return "personalAccount";
    }

    @PostMapping("/personalAccount/createTask")
    public String createTask(@RequestParam List<String> selectedGroups, @RequestParam String message, @RequestParam String deadline, Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        List<String> numberGroups = userRepository.findAllNumberGroup();
        model.addAttribute("numberGroups", numberGroups);
        System.out.println(selectedGroups);
        // создание таска скорее всего надо перенести в тасксервис
        for (String numberGroupElement: selectedGroups){
            List<User> usersList= userRepository.findAllByNumberGroup(numberGroupElement);
            for (User certainUser: usersList){
                Task task = new Task();
                task.setMessage(message);
                task.setSender(userService.getUserByPrincipal(principal));
                task.setUser(certainUser);
                task.setDeadline(deadline);
                task.setStage("Не выполнено");
                System.out.println(task);
                taskRepository.save(task);
                model.addAttribute("tasks", taskRepository.findAll());
            }
        }

        return "personalAccount";
    }

    @PostMapping("/personalAccount/execute")
    public String executeTask(@RequestParam String executeTaskId, Model model, Principal principal){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        Optional<Task> currentTask = taskRepository.findTaskById(Long.parseLong((String)executeTaskId));
        Task desiredTask;
        if (currentTask.isPresent()){
            desiredTask = currentTask.get();
            desiredTask.setStage("Задача выполнена");
            taskRepository.save(desiredTask);
        }

        return "redirect:/personalAccount";
    }

    @PostMapping("/personalAccount/delete")
    public String deleteTask(Model model, Principal principal, @RequestParam String deleteTaskId){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        Optional<Task> currentTask = taskRepository.findTaskById(Long.parseLong((String)deleteTaskId));
        Task desiredTask;
        if (currentTask.isPresent()){
            desiredTask = currentTask.get();
            taskRepository.deleteById(desiredTask.getId());
        }
        return "redirect:/personalAccount";
    }

    @PostMapping("/personalAccount/feedback")
    public String feedbackToStudent(Model model, Principal principal, @RequestParam String feedbackTaskId, @RequestParam String feedbackInput){
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        Optional<Task> currentTask = taskRepository.findTaskById(Long.parseLong((String) feedbackTaskId));
        Task desiredTask;
        if (currentTask.isPresent()){
            desiredTask = currentTask.get();
            desiredTask.setStage("Отправлены правки");
            taskRepository.save(desiredTask);
            String studentMail = desiredTask.getUser().getMail();
            String teacherName = desiredTask.getSender().getName();
            Sender sender = new Sender();
            sender.sendFeedbackToStudent(studentMail, teacherName, feedbackInput);
        }
        return "redirect:/personalAccount";
    }
}
//http://localhost:8080/personalAccount?qwe=имя