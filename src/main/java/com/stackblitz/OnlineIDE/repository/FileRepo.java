package com.stackblitz.OnlineIDE.repository;

import com.stackblitz.OnlineIDE.model.Files;
import com.stackblitz.OnlineIDE.model.Folders;
import com.stackblitz.OnlineIDE.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface FileRepo extends JpaRepository<Files, Long> {
    List<Files> findByProjectAndFolderIsNull(Project project);

    List<Files> findByProjectAndFolder(Project project, Folders folder);

    Page<Files> findByProjectAndFolder(Project project, Folders folder, Pageable pageable);

    @Transactional
    @Modifying
    Integer deleteByProject(Project project);

    void deleteByFolderId(Long folderId);



    //content_json::jsonb: Converts the content_json column to JSONB type, so you can use JSON functions on it.
    //cast(:path as text[]): Turns the path you provide (like {key}) into an array of text, which tells PostgreSQL where in the JSON to update.
    //to_jsonb(cast(:newValue as text)): Takes your new value, makes sure it’s text, and then converts it to JSONB so it can be stored in the JSON column.

    @Modifying
    @Query(value = "UPDATE files " +
            "SET content_json = jsonb_set(content_json::jsonb, cast(:path as text[]), to_jsonb(cast(:newValue as text)), true), " +
            "updated_at = now() " +
            "WHERE id = :fileId", nativeQuery = true)
    void updateLine(@Param("fileId") Long fileId,
                    @Param("path") String path,
                    @Param("newValue") String newValue);
}
