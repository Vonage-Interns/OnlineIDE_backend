package com.stackblitz.OnlineIDE.repository;

import com.stackblitz.OnlineIDE.model.Folders;
import com.stackblitz.OnlineIDE.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepo extends JpaRepository<Folders, Long> {
//    List<FolderDTO> findByProjectIDAndPArentFolderRef(Long projectId, Long parentFolderRef, String projectName);

    List<Folders> findByProjectAndParentFolderIdIsNull(Project project);
    List<Folders> findByProjectAndParentFolderId(Project project, Folders parentFolderId);

    Integer deleteByProject(Project project);


<<<<<<< HEAD
=======


>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
    boolean existsByProject_IdAndParentFolderId_IdAndName(Long projectId, Long parentFolderId, String name);

    // For root folders (parent is NULL)
    boolean existsByProject_IdAndParentFolderIdIsNullAndName(Long projectId, String name);
}


