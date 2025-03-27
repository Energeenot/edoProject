package com.example.edo.services;

import com.example.edo.models.Files;
import com.example.edo.models.Task;
import com.example.edo.models.User;
import com.example.edo.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public void createTask(List<String> selectedGroups, String message, String deadline, User sender){
        System.out.println(selectedGroups);
        for (String numberGroupElement: selectedGroups) {
            List<User> usersList = userService.getAllUsersByNumberGroup(numberGroupElement);
            for (User certainUser : usersList) {
                Task task = new Task();
                task.setMessage(message);
                task.setSender(sender);
                task.setUser(certainUser);
                task.setDeadline(deadline);
                task.setStage("Не выполнено");
                System.out.println(task);
                taskRepository.save(task);
            }
        }
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public List<Task> getAllTasksByUserId(Long userId){
        return taskRepository.findAllByUserId(userId);
    }

    public void executeTask(String executeTaskId){
        Optional<Task> currentTask = taskRepository.findTaskById(Long.parseLong(executeTaskId));
        Task desiredTask;
        if (currentTask.isPresent()){
            desiredTask = currentTask.get();
            desiredTask.setStage("Задача выполнена");
            taskRepository.save(desiredTask);
        }
    }

    public void deleteTask(String deleteTaskId){
        Optional<Task> currentTask = taskRepository.findTaskById(Long.parseLong(deleteTaskId));
        Task desiredTask;
        if (currentTask.isPresent()){
            desiredTask = currentTask.get();
            taskRepository.deleteById(desiredTask.getId());
        }
    }

    public Task getTaskById(Long taskId){
        return taskRepository.findTaskById(taskId).orElse(null);
    }

    public void updateTaskWithFiles(Task desiredTask, Files savedFiles){
        desiredTask.setFiles(savedFiles);
        desiredTask.setStage("Документы отправлены");
        taskRepository.save(desiredTask);
    }


    public void updateTaskStage(Task task, String stage) {
        task.setStage(stage);
        taskRepository.save(task);
    }
}
