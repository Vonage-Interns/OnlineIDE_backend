package com.stackblitz.OnlineIDE.controller;


import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.service.FolderService;
import com.stackblitz.OnlineIDE.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    private final FolderService folderService;

    //get all projects

    @GetMapping
    public ResponseEntity<?> getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {
        try {

            String userId = userDetails.getUsername();

            List<ProjectListDTO> projects = projectService.getProjects(userId);


            ApiResponse apiResponse = new ApiResponse("Projects fetched", projects);

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Fetch Projects");
        }
    }


    @GetMapping("/{projectId}/root")
    public ResponseEntity<?> getProjectRootItems(@PathVariable Long projectId, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String userId = userDetails.getUsername();

            ProjectTreeResponse response = folderService.getRootTree(projectId, userId);
            return ResponseEntity.ok(new ApiResponse("Root items fetched", response));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch root items");

        }
    }


    @GetMapping("/{projectId}/folders/{folderId}/children")
    public ResponseEntity<FolderChildrenResponseDTO> getFolderChildren(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {


        String userId = userDetails.getUsername();

        FolderChildrenResponseDTO response = folderService.getFolderChildren(projectId, folderId, userId, page, size);

        ApiResponse<FolderChildrenResponseDTO> apiResponse = new ApiResponse(
                "Created project successfully",
                response
        );
        return ResponseEntity.ok(response);
    }




    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {


        String userId = userDetails.getUsername();

        ProjectDTO createdProject = projectService.createProject(projectDTO, userId);

        ApiResponse<ProjectDTO> apiResponse = new ApiResponse(
                "Created project successfully",
                createdProject
        );

        return ResponseEntity.ok(apiResponse);

    }

    @PatchMapping("/updateProjectName")
    public ResponseEntity<ApiResponse<ProjectDTO>> updateProjectName(

            @RequestBody UpdateProjectName updateProjectName, @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();


        ProjectDTO updatedProject = projectService.updateProjectName(updateProjectName, userId);

        ApiResponse<ProjectDTO> response = new ApiResponse<>(
                "Updated project name successfully",
                updatedProject
        );

        return ResponseEntity.ok(response);
    }



    //delete project each and every folder and files i have to delete

    @DeleteMapping("{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable long projectId,  @AuthenticationPrincipal UserDetails userDetails) {
        try {


            String userId = userDetails.getUsername();

            ProjectDTO deletedProject = projectService.deleteProject(projectId, userId);

            ProjectCreationResponse projectCreationResponse = new ProjectCreationResponse(deletedProject);

            ApiResponse<ProjectCreationResponse> apiResponse = new ApiResponse<>(
                    "Project deleted successfully",
                    projectCreationResponse
            );
            return ResponseEntity.ok().body(apiResponse);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }
}
