package com.stackblitz.OnlineIDE.service;

import com.stackblitz.OnlineIDE.dto.FolderDTO;
import com.stackblitz.OnlineIDE.dto.FolderResponseDTO;
import com.stackblitz.OnlineIDE.dto.FolderTreeDTO;
import com.stackblitz.OnlineIDE.dto.UpdateName;
import com.stackblitz.OnlineIDE.exceptions.FolderNotFoundException;
import com.stackblitz.OnlineIDE.exceptions.ProjectNotFoundException;
import com.stackblitz.OnlineIDE.model.Folders;
import com.stackblitz.OnlineIDE.model.Project;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.repository.FolderRepo;
import com.stackblitz.OnlineIDE.repository.ProjectRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FoldersService {

    private final FolderRepo folderRepo;

    private final ProjectRepo projectRepo;

    private final FileRepo fileRepo;

    public FolderResponseDTO createFolder(FolderDTO folderDTO, String userId) {
        Project project = projectRepo.findById(folderDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getUser().getId().equals(Long.valueOf(userId))) {
            throw new RuntimeException("Unauthorized access to this project");
        }

        boolean exists;
        if (folderDTO.getParentFolderId() == null) {
            exists = folderRepo.existsByProject_IdAndParentFolderIdIsNullAndName(
                    project.getId(),
                    folderDTO.getName()
            );
        } else {
            exists = folderRepo.existsByProject_IdAndParentFolderId_IdAndName(
                    project.getId(),
                    folderDTO.getParentFolderId(),
                    folderDTO.getName()
            );
        }

        if (exists) {
            throw new IllegalArgumentException("Folder with this name already exists here");
        }

        Folders folder = new Folders();
        folder.setName(folderDTO.getName());
        folder.setProject(project);
        folder.setParentFolderId(
                folderDTO.getParentFolderId() == null ? null : folderRepo.findById(folderDTO.getParentFolderId())
                        .orElseThrow(() -> new RuntimeException("Parent folder not found"))
        );
        folder.setCreated_at(LocalDateTime.now().withNano(0));
        folder.setUpdated_at(LocalDateTime.now().withNano(0));

        Folders saved = folderRepo.save(folder);

        return FolderResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .projectId(saved.getProject().getId())
                .parentFolderId(
                        saved.getParentFolderId() != null ? saved.getParentFolderId().getId() : null
                )
                .build();
    }


    public FolderResponseDTO updateFolderName(UpdateName updateName, String userId) {
        Folders folder = folderRepo.findById(updateName.getFolderId())
                .orElseThrow(() -> new RuntimeException("Folder not found"));


        if (!folder.getProject().getUser().getId().equals(Long.valueOf(userId))) {
            throw new RuntimeException("Unauthorized access to this folder");
        }

        boolean exists;
        if (folder.getParentFolderId() == null) {
            exists = folderRepo.existsByProject_IdAndParentFolderIdIsNullAndName(
                    folder.getProject().getId(),
                    updateName.getUpdateName()
            );
        } else {
            exists = folderRepo.existsByProject_IdAndParentFolderId_IdAndName(
                    folder.getProject().getId(),
                    folder.getParentFolderId().getId(),
                    updateName.getUpdateName()
            );
        }

        if (exists) {
            throw new IllegalArgumentException("Folder with this name already exists here");
        }

        folder.setName(updateName.getUpdateName());
        folder.setUpdated_at(LocalDateTime.now().withNano(0));

        Folders saved = folderRepo.save(folder);

        return FolderResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .projectId(saved.getProject().getId())
                .parentFolderId(saved.getParentFolderId() != null ? saved.getParentFolderId().getId() : null)
                .build();
    }

    @Transactional
    public FolderTreeDTO deleteFolder(long folderId, String userId) {

        Folders folders = folderRepo.findById(folderId).orElseThrow(() -> new FolderNotFoundException("Folder not found"));
        fileRepo.deleteByFolderId(folderId);
        folderRepo.deleteById(folderId);

        FolderTreeDTO folderTreeDTO = new FolderTreeDTO(
                folders.getName()
        );

        return folderTreeDTO;


    }
}
