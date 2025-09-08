package com.stackblitz.OnlineIDE.service;

<<<<<<< HEAD
import com.stackblitz.OnlineIDE.common.ApiResponse;
=======
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
import com.stackblitz.OnlineIDE.dto.ProjectDTO;
import com.stackblitz.OnlineIDE.dto.ProjectListDTO;
import com.stackblitz.OnlineIDE.dto.UpdateProjectName;
import com.stackblitz.OnlineIDE.exceptions.DuplicateProjectException;
import com.stackblitz.OnlineIDE.exceptions.ProjectNotFoundException;
import com.stackblitz.OnlineIDE.exceptions.UserNotFoundException;
import com.stackblitz.OnlineIDE.model.Project;
import com.stackblitz.OnlineIDE.model.Users;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.repository.FolderRepo;
import com.stackblitz.OnlineIDE.repository.ProjectRepo;
import com.stackblitz.OnlineIDE.repository.UserRepo;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
=======
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProjectService {


    private final ProjectRepo projectRepo;

    private final UserRepo userRepo;

    private final FolderRepo folderRepo;

    private final FileRepo fileRepo;

    public ProjectDTO createProject(ProjectDTO projectDTO, String userId){

            Users user = userRepo.findById(Integer.valueOf(userId)).orElseThrow(() -> new UserNotFoundException("User not found"));
            List<ProjectListDTO> getUserProjects = getProjects(userId);
            boolean projectExists = getUserProjects.stream()
                    .anyMatch(p -> p.getProjectName().equalsIgnoreCase(projectDTO.getName()));

            if (projectExists) {
                throw new DuplicateProjectException("Project with the same name already exists.");
            }

            Project project = new Project();
            project.setName(projectDTO.getName());
            project.setUser(user);
            project.setCreated_at(LocalDateTime.now().withNano(0));
            project.setUpdated_at(LocalDateTime.now().withNano(0));
           project.setKey(UUID.randomUUID().toString().replace("-", ""));

            Project saved =  projectRepo.save(project);

            ProjectDTO dto = new ProjectDTO();
            dto.setName(saved.getName());
            dto.setId(saved.getId());

            return dto;

    }

    public List<ProjectListDTO> getProjects(String userId) {
        Users user = userRepo.findById(Integer.valueOf(userId)).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Project> projects = projectRepo.findByUser(user);

        return projects.stream()
                .map(project -> new ProjectListDTO(project.getId(), project.getName()))
                .toList();
    }

<<<<<<< HEAD

=======
    @Transactional
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
    public ProjectDTO deleteProject(long projectId, String userId){
        Project project  = projectRepo.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        if (!project.getUser().getId().equals(Long.valueOf(userId))) {
            throw new AccessDeniedException("You are not allowed to access this project");
        }

        fileRepo.deleteByProject(project);
        folderRepo.deleteByProject(project);
        projectRepo.deleteById(projectId);

        ProjectDTO dto = new ProjectDTO();
        dto.setName(project.getName());
        return dto;

    }

    public ProjectDTO updateProjectName(UpdateProjectName updateProjectName, String userId) {
        Users user = userRepo.findById(Integer.valueOf(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Project project = projectRepo.findById(updateProjectName.getId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));


        if (!project.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to this folder");
        }

        List<ProjectListDTO> userProjects = getProjects(userId);
        boolean projectExists = userProjects.stream()
                .anyMatch(p -> p.getProjectName().equalsIgnoreCase(updateProjectName.getUpdateName()));

        if (projectExists) {
            throw new DuplicateProjectException("Project with the same name already exists.");
        }

        project.setName(updateProjectName.getUpdateName());
        project.setUpdated_at(LocalDateTime.now().withNano(0));

        Project saved = projectRepo.save(project);

        ProjectDTO dto = new ProjectDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());

        return dto;
    }
}
