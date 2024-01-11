package com.example.edo.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Getter
@Entity
@Table(name = "Files")
@Data
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;
    @Column(name = "uniqueName", unique = true)
    private String uniqueName;
    @Column(name = "uniqueGroupCode")
    private String uniqueGroupCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private User sender;
}
