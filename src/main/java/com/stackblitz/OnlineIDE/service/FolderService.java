package com.stackblitz.OnlineIDE.service;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.exceptions.ProjectNotFoundException;
import com.stackblitz.OnlineIDE.model.Files;
import com.stackblitz.OnlineIDE.model.Folders;
import com.stackblitz.OnlineIDE.model.Project;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.repository.FolderRepo;
import com.stackblitz.OnlineIDE.repository.ProjectRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final ProjectRepo projectRepo;

    private final FolderRepo folderRepo;

    private final FileRepo fileRepo;



    public ProjectTreeResponse getRootTree(Long projectId, String userId) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getUser().getId().equals(Long.valueOf(userId))) {
            throw new AccessDeniedException("You are not allowed to access this project");
        }

        // Root folders (parent_folder_id = null)
        List<Folders> rootFolders = folderRepo.findByProjectAndParentFolderIdIsNull(project);
        List<FolderTreeDTO> folderDTOs = rootFolders.stream()
                .map(folder -> FolderTreeDTO.builder()
                        .id(folder.getId())
                        .name(folder.getName())
                        .build())
                .toList();

        // Root files (folder_id = null)
        List<Files> rootFiles = fileRepo.findByProjectAndFolderIsNull(project);
        List<FileTreeDTO> fileDTOs = rootFiles.stream()
                .map(file -> FileTreeDTO.builder()
                        .id(file.getId())
                        .name(file.getName())
                        .type(file.getType())
                        .build())
                .toList();

        return ProjectTreeResponse.builder()
                .folders(folderDTOs)
                .files(fileDTOs)
                .build();
    }

//    public FolderChildrenResponseDTO getFolderChildren(Long projectId, Long folderId, String userId) {
//        Project project = projectRepo.findById(projectId)
//                .orElseThrow(() -> new RuntimeException("Project not found"));
//
//        if (!project.getUser().getId().equals(Long.valueOf(userId))) {
//            throw new RuntimeException("Unauthorized access to this project");
//        }
//
//
//        Folders parentFolder = folderRepo.findById(folderId)
//                .orElseThrow(() -> new RuntimeException("Folder not found"));
//
//        List<FolderChildDTO> subFolders = folderRepo.findByProjectAndParentFolderId(project, parentFolder)
//                .stream()
//                .map(folder -> FolderChildDTO.builder()
//                        .id(folder.getId())
//                        .name(folder.getName())
//                        .build())
//                .toList();
//
//        List<FileResponseDTO> files = fileRepo.findByProjectAndFolder(project, parentFolder)
//                .stream()
//                .map(file -> FileResponseDTO.builder()
//                        .id(file.getId())
//                        .name(file.getName())
//                        .type(file.getType())
//                        .content(file.getContent())
//                        .build())
//                .toList();
//
//        return FolderChildrenResponseDTO.builder()
//                .folders(subFolders)
//                .files(files)
//                .build();
//    }

    public FolderChildrenResponseDTO getFolderChildren(Long projectId, Long folderId, String userId, int page, int size) {
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (!project.getUser().getId().equals(Long.valueOf(userId))) {
            throw new RuntimeException("Unauthorized access to this project");
        }

        Folders parentFolder = folderRepo.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));

        if (!parentFolder.getProject().getId().equals(projectId)) {
            throw new RuntimeException("Folder does not belong to this project");
        }

        List<FolderChildDTO> subFolders = folderRepo.findByProjectAndParentFolderId(project, parentFolder)
                .stream()
                .map(folder -> FolderChildDTO.builder()
                        .id(folder.getId())
                        .name(folder.getName())
                        .build())
                .toList();


        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Files> filePage = fileRepo.findByProjectAndFolder(project, parentFolder, pageable);

        List<FileResponseDTO> files = filePage.getContent().stream()
                .map(file -> FileResponseDTO.builder()
                        .id(file.getId())
                        .name(file.getName())
                        .type(file.getType())
                        .content(file.getContentJson())
                        .build())
                .toList();

        return FolderChildrenResponseDTO.builder()
                .folders(subFolders)
                .files(files)
                .totalFiles(filePage.getTotalElements()) // for frontend
                .hasMore(filePage.hasNext()) // for frontend to know if "Load More" should show
                .build();
    }

}
