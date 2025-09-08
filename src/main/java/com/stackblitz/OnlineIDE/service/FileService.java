package com.stackblitz.OnlineIDE.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.exceptions.FileNotFoundException;
import com.stackblitz.OnlineIDE.exceptions.ProjectNotFoundException;
import com.stackblitz.OnlineIDE.exceptions.UserNotFoundException;
import com.stackblitz.OnlineIDE.model.Files;
import com.stackblitz.OnlineIDE.model.Folders;
import com.stackblitz.OnlineIDE.model.Project;
import com.stackblitz.OnlineIDE.model.Users;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.repository.FolderRepo;
import com.stackblitz.OnlineIDE.repository.ProjectRepo;
import com.stackblitz.OnlineIDE.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileService {

    private final UserRepo UserRepo;

    private final FileRepo filesRepository;

    private final ProjectRepo projectsRepository;

    private final FolderRepo folderRepository;

    public FileResponseDTO  getFileById(Long id, String userId) {

<<<<<<< HEAD
        Users user = UserRepo.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Files file = filesRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
=======
        Users user = UserRepo.findById(Integer.valueOf(userId)).orElseThrow(() -> new UserNotFoundException("User not found"));
        Files file = filesRepository.findById(id).orElseThrow(() -> new FileNotFoundException("File not found"));
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

        return FileResponseDTO.builder()
                .id(file.getId())
                .name(file.getName())
                .type(file.getType())
                .content(file.getContentJson())
                .build();
    }

    public FileResponseDTO createFile(FileDTO dto, String userId) {
        Project project =  null;
        if(dto.getProjectID() != null) {
            project = projectsRepository.findById(dto.getProjectID())
                    .orElseThrow(() -> new ProjectNotFoundException("Project not found"));
        }
        Folders folder = null;
        if (dto.getFolderID() != null) {
            folder = folderRepository.findById(dto.getFolderID())
                    .orElseThrow(() -> new FileNotFoundException("Folder not found"));
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String contentJsonString;
        try {
            contentJsonString = objectMapper.writeValueAsString(dto.getContent()); //json content
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize content to JSON", e);
        }

        Files file = Files.builder()
                .name(dto.getFileName())
                .type(dto.getType())
<<<<<<< HEAD
                .contentJson(contentJsonString)
=======
                .contentJson(contentJsonString) // update for content json
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
                .sizeInKb((long) (contentJsonString.length() / 1024))
                .project(project)
                .folder(folder)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        filesRepository.save(file);

        return FileResponseDTO.builder()
                .id(file.getId())
                .name(file.getName())
                .type(file.getType())
<<<<<<< HEAD
                .content(file.getContentJson())
                .build();
    }

    @Transactional
    public FileTreeDTO deleteFile(long fileId, String userId) {

        Users user = UserRepo.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        Files file = filesRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));

=======
                .content(file.getContentJson()) //update for content json
                .build();
    }


    public FileTreeDTO deleteFile(long fileId) {
        Files file = filesRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        filesRepository.deleteById(fileId);
        FileTreeDTO fileTreeDTO = new FileTreeDTO(
                file.getName()
        );
        return fileTreeDTO;
    }

    @Transactional
<<<<<<< HEAD
    public FileTreeDTO updateFileContent(long fileId, UpdateFileContentRequest request, String userId) {

        Users user = UserRepo.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Files updatedFile = filesRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
=======
    public FileTreeDTO updateFileContent(long fileId, UpdateFileContentRequest request) {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

        if (request.getContent() == null || request.getContent().isEmpty()) {
            throw new IllegalArgumentException("No updates provided");
        }
<<<<<<< HEAD
=======
        // fetch updated file to return DTO
        Files updatedFile = filesRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found"));
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)


        for (Map.Entry<String, String> entry : request.getContent().entrySet()) {
            String path = "{" + entry.getKey() + "}";
            filesRepository.updateLine(fileId, path, entry.getValue());
        }

<<<<<<< HEAD
=======

>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        FileTreeDTO dto = new FileTreeDTO();
        dto.setId(updatedFile.getId());
        dto.setName(updatedFile.getName());
        dto.setType(updatedFile.getType());
        return dto;
    }

<<<<<<< HEAD
    public FileNameUpdateDTO UpdateFileName(FileNameUpdateDTO fileNameUpdateDTO, String userId){

        Users user = UserRepo.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Files files = filesRepository.findById(fileNameUpdateDTO.getFileId())
                .orElseThrow(() -> new FileNotFoundException("File not found"));

=======
    public FileNameUpdateDTO UpdateFileName(FileNameUpdateDTO fileNameUpdateDTO){
        Files files = filesRepository.findById(fileNameUpdateDTO.getFileId()).orElseThrow(() -> new FileNotFoundException("File not found"));
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        files.setName(fileNameUpdateDTO.getFileName());
        filesRepository.save(files);

        FileNameUpdateDTO dto = new FileNameUpdateDTO();
        dto.setFileId(files.getId());
        dto.setFileName(files.getName());
        return dto;

    }
}





