package com.example.edo.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Files")
@Data
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;
    @Column(name = "unique_name", unique = true)
    private String uniqueName;
    @Column(name = "unique_group_code")
    private String uniqueGroupCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private User sender;
}
