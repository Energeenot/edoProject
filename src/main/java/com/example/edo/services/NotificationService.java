package com.example.edo.services;

import com.example.edo.dto.MessageDto;
import com.example.edo.kafka.SenderProducer;
import com.example.edo.models.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private static final Pattern EMAIL_PATTERN = Pattern
            .compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private final SenderProducer producer;

    public void notifyTeacher(Task task, String uniqueID, String messageToTeacher){
        String teacherMail = task.getSender().getEmail();
        if (!EMAIL_PATTERN.matcher(teacherMail).matches()) {
            throw new IllegalArgumentException("Почта преподавателя указана неверно");
        } else {
            producer.sendNotificationOfNewDocuments(MessageDto.builder()
                    .uniqueCode(uniqueID)
                    .toEmail(teacherMail)
                    .fio(task.getUser().getFullName())
                    .numberGroup(task.getUser().getNumberGroup())
                    .messageToTeacher(messageToTeacher)
                    .build());
        }
    }

    public void sendFeedbackToStudent(String feedbackInput, Task desiredTask){
            String studentMail = desiredTask.getUser().getEmail();
            String teacherName = desiredTask.getSender().getFullName();

            producer.sendFeedback(MessageDto.builder()
                    .teacherName(teacherName)
                    .toEmail(studentMail)
                    .feedback(feedbackInput)
                    .build());

    }
}
