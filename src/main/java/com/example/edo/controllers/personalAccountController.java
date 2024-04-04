package com.example.edo.controllers;

import com.example.edo.models.Task;
import com.example.edo.models.User;
import com.example.edo.repositories.TaskRepository;
import com.example.edo.repositories.UserRepository;
import com.example.edo.services.UserService;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class personalAccountController {
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
//            List<String> numberGroupsList = new ArrayList<>();
            if (!allTasks.isEmpty()){
                model.addAttribute("tasks", allTasks);
//                for (Task task: allTasks){
////                     numberGroupsList.add(task.getUser().getNumberGroup());
//                    System.out.println(task);
//                    model.addAttribute("taskMessage", task.getMessage());
//                    model.addAttribute("studentName", task.getUser().getName());
//                    model.addAttribute("taskStage", task.getStage());
//                    if (task.getFiles() == null){
//                        model.addAttribute("taskUniqueGroupCode", "Не отправлен");
//                    }else {
//                        model.addAttribute("taskUniqueGroupCode", task.getFiles().getUniqueGroupCode());
//                    }
//                    model.addAttribute("taskDeadline", task.getDeadline());
//                }
//                System.out.println(numberGroupsList);
//                model.addAttribute("allTasks", allTasks);
                System.out.println(allTasks);
            }

//            System.out.println(model.getAttribute("numberGroups"));
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
//                System.out.println(taskRepository.findAllTasksWithReplacedNull());
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
//        desiredTask = curentTask.get();

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
}
//http://localhost:8080/personalAccount?qwe=имя