
package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.service.FolderService;
import com.stackblitz.OnlineIDE.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Mock
    private FolderService folderService;

    @Mock
    private UserDetails userDetails;

    private final String userId = "42";

    @BeforeEach
    void setUp() {
        when(userDetails.getUsername()).thenReturn(userId);
    }

    @Test
    void getAllProjects_shouldReturnProjects() {
        List<ProjectListDTO> projects = Arrays.asList(
                new ProjectListDTO(1L, "Project1"),
                new ProjectListDTO(2L, "Project2")
        );
        when(projectService.getProjects(userId)).thenReturn(projects);

        ResponseEntity<?> response = projectController.getAllProjects(userDetails);

        assertEquals(200, response.getStatusCode().value());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("Projects fetched", apiResponse.getMessage());
        assertEquals(projects, apiResponse.getData());
    }

    @Test
    void getProjectRootItems_shouldReturnRootItems() {
        ProjectTreeResponse treeResponse = ProjectTreeResponse.builder().build();
        when(folderService.getRootTree(1L, userId)).thenReturn(treeResponse);

        ResponseEntity<?> response = projectController.getProjectRootItems(1L, userDetails);

        assertEquals(200,response.getStatusCode().value());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("Root items fetched", apiResponse.getMessage());
        assertEquals(treeResponse, apiResponse.getData());
    }

    @Test
    void getFolderChildren_shouldReturnChildren() {
        FolderChildrenResponseDTO childrenResponse = FolderChildrenResponseDTO.builder()
                .totalFiles(2)
                .hasMore(false)
                .build();
        when(folderService.getFolderChildren(1L, 2L, userId, 0, 10)).thenReturn(childrenResponse);

        ResponseEntity<FolderChildrenResponseDTO> response = projectController.getFolderChildren(1L, 2L, 0, 10, userDetails);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(childrenResponse, response.getBody());
    }

    @Test
    void createProject_shouldReturnCreatedProject() {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(1L);
        projectDTO.setName("NewProject");

        when(projectService.createProject(projectDTO, userId)).thenReturn(projectDTO);

        ResponseEntity<?> response = projectController.createProject(projectDTO, userDetails);

        assertEquals(200, response.getStatusCode().value());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("Created project successfully", apiResponse.getMessage());
        assertEquals(projectDTO, apiResponse.getData());
    }

    @Test
    void updateProjectName_shouldReturnUpdatedProject() {
        UpdateProjectName updateProjectName = new UpdateProjectName();
        updateProjectName.setId(1L);
        updateProjectName.setUpdateName("UpdatedName");

        ProjectDTO updatedProject = new ProjectDTO();
        updatedProject.setId(1L);
        updatedProject.setName("UpdatedName");

        when(projectService.updateProjectName(updateProjectName, userId)).thenReturn(updatedProject);

        ResponseEntity<ApiResponse<ProjectDTO>> response = projectController.updateProjectName(updateProjectName, userDetails);

        assertEquals(200,response.getStatusCode().value());
        ApiResponse<ProjectDTO> apiResponse = response.getBody();
        assertEquals("Updated project name successfully", apiResponse.getMessage());
        assertEquals(updatedProject, apiResponse.getData());
    }

    @Test
    void deleteProject_shouldReturnDeletedProject() {
        ProjectDTO deletedProject = new ProjectDTO();
        deletedProject.setId(1L);
        deletedProject.setName("DeletedProject");

        when(projectService.deleteProject(1L, userId)).thenReturn(deletedProject);

        ResponseEntity<?> response = projectController.deleteProject(1L, userDetails);

        assertEquals(200, response.getStatusCode().value());
        ApiResponse apiResponse = (ApiResponse) response.getBody();
        ProjectCreationResponse projectCreationResponse = (ProjectCreationResponse) apiResponse.getData();
        assertEquals("Project deleted successfully", apiResponse.getMessage());
        assertEquals(deletedProject, projectCreationResponse.getProjectDTO());
    }
}