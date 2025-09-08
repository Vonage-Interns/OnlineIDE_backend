package com.stackblitz.OnlineIDE.model;


import jakarta.persistence.*;
<<<<<<< HEAD
=======
import lombok.Builder;
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "folder" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"project_id", "parent_folder_id", "name"})
})
<<<<<<< HEAD
=======

>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
public class Folders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_folder_id")
    private Folders parentFolderId;

    @OneToMany(mappedBy = "parentFolderId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folders> childFolders = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime created_at = LocalDateTime.now().withNano(0);

    @Column
    private LocalDateTime updated_at = LocalDateTime.now().withNano(0);

}


