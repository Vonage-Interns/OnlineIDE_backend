package com.stackblitz.OnlineIDE.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import java.time.LocalDateTime;

@Data
@Entity(name = "files")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folders folder;

    @Column(nullable = false)
    private String name;

    @Column(name = "file_type", nullable = false)
    private String type;

<<<<<<< HEAD
=======
    //    @Column(columnDefinition = "TEXT")
    //    private String content;

    //content in json columndefination in json format
    //    Hibernate will send the String value as-is, but cast it to JSON in the SQL statement.
    //    Postgres accepts the casted value and stores it as JSON.
    //    On reading, Postgres casts JSON back to text, so you get a String in Java.
    // This will store the content as JSON
    // The @ColumnTransformer is used to handle the conversion between JSON and text
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
    @Column(columnDefinition = "json")
    @ColumnTransformer(
            write = "?::json",
            read = "content_json::text"
    )
    private String contentJson;

    @Column(nullable = false)
    private Long sizeInKb;

    @Column(name = "createdAt") //smallcase
    private LocalDateTime createdAt = LocalDateTime.now().withNano(0);

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now().withNano(0);
}
