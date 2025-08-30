package com.stackblitz.OnlineIDE.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now().withNano(0);

    @Column(nullable = false)
    private LocalDateTime updated_at = LocalDateTime.now().withNano(0);

    @Column(nullable = false, unique = true)
    private String key;





}
