package com.example.edo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MessageDto {

    private String uniqueCode;
    private String toEmail;
    private String fio;
    private String numberGroup;
    private String messageToTeacher;
    private String token;
    private String teacherName;
    private String feedback;
}
