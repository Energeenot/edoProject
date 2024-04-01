package com.example.edo.services;

import com.example.edo.models.Task;
import com.example.edo.models.User;
import com.example.edo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
//    public String createTask(Task task){
//        List<User> users = new ArrayList<>();
//        users = userRepository.findAllByNumberGroup(task.getUser().getNumberGroup());
//
//    }
}
