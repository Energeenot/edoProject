package com.example.edo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="task")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unique_group_code", referencedColumnName = "unique_group_code")
    private Files files;
    @Column(name = "message")
    private String message;
    @Column(name = "stage")
    private String stage;
    @Column(name = "deadline")
    private String deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private User sender;


}
