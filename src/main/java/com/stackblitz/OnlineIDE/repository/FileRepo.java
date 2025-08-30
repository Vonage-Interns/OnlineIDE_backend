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

import java.util.List;

@Repository
public interface FileRepo extends JpaRepository<Files, Long> {
    List<Files> findByProjectAndFolderIsNull(Project project);

    List<Files> findByProjectAndFolder(Project project, Folders folder);

    Page<Files> findByProjectAndFolder(Project project, Folders folder, Pageable pageable);

    Integer deleteByProject(Project project);

    void deleteByFolderId(Long folderId);



    @Modifying
    @Query(value = "UPDATE files " +
            "SET content_json = jsonb_set(content_json::jsonb, cast(:path as text[]), to_jsonb(cast(:newValue as text)), true), " +
            "updated_at = now() " +
            "WHERE id = :fileId", nativeQuery = true)
    void updateLine(@Param("fileId") Long fileId,
                    @Param("path") String path,
                    @Param("newValue") String newValue);
}
