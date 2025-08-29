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
