package com.stackblitz.OnlineIDE.controller;


import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.service.FolderService;
<<<<<<< HEAD
import com.stackblitz.OnlineIDE.service.JWTservice;
=======
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
import com.stackblitz.OnlineIDE.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.core.context.SecurityContextHolder;
=======
import org.springframework.security.core.annotation.AuthenticationPrincipal;
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectControler {

    private final ProjectService projectService;

<<<<<<< HEAD
    private final JWTservice jwTservice;

    private final FolderService folderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectListDTO>>> getAllProjects() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

=======
    private final FolderService folderService;

    //get all projects

    @GetMapping
    public ResponseEntity<?> getAllProjects(@AuthenticationPrincipal UserDetails userDetails) {
        try {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
            String userId = userDetails.getUsername();

            List<ProjectListDTO> projects = projectService.getProjects(userId);

<<<<<<< HEAD
            ApiResponse<List<ProjectListDTO>> apiResponse = new ApiResponse<>("Projects fetched", projects);
=======
            ApiResponse apiResponse = new ApiResponse("Projects fetched", projects);
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            e.printStackTrace();
<<<<<<< HEAD
            ApiResponse<List<ProjectListDTO>> errorResponse = new ApiResponse<>(
                    "Failed to Fetch Projects",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }
    }

    @GetMapping("/{projectId}/root")
    public ResponseEntity<ApiResponse<ProjectTreeResponse>> getProjectRootItems(@PathVariable Long projectId) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
            String userId = userDetails.getUsername();

            ProjectTreeResponse response = folderService.getRootTree(projectId, userId);

            return ResponseEntity.ok(new ApiResponse("Root items fetched", response));

        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<ProjectTreeResponse> errorResponse = new ApiResponse<>(
                    "Failed to Fetch root items",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

=======
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
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        }
    }


    @GetMapping("/{projectId}/folders/{folderId}/children")
    public ResponseEntity<FolderChildrenResponseDTO> getFolderChildren(
            @PathVariable Long projectId,
            @PathVariable Long folderId,
            @RequestParam(defaultValue = "0") int page,
<<<<<<< HEAD
            @RequestParam(defaultValue = "10") int size) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
=======
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserDetails userDetails) {

>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

        String userId = userDetails.getUsername();

        FolderChildrenResponseDTO response = folderService.getFolderChildren(projectId, folderId, userId, page, size);

        ApiResponse<FolderChildrenResponseDTO> apiResponse = new ApiResponse(
                "Created project succesfully",
                response
        );
        return ResponseEntity.ok(response);
    }

<<<<<<< HEAD
    @PostMapping
    public ResponseEntity<ApiResponse<ProjectDTO>> createProject(@RequestBody ProjectDTO projectDTO) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
=======


    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO,
                                           @AuthenticationPrincipal UserDetails userDetails) {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

        String userId = userDetails.getUsername();

        ProjectDTO createdProject = projectService.createProject(projectDTO, userId);

        ApiResponse<ProjectDTO> apiResponse = new ApiResponse(
                "Created project succesfully",
                createdProject
        );

        return ResponseEntity.ok(apiResponse);

    }

    @PatchMapping("/updateProjectName")
    public ResponseEntity<ApiResponse<ProjectDTO>> updateProjectName(
<<<<<<< HEAD
            @RequestBody UpdateProjectName updateProjectName) {

        String userId = ((UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
=======
            @RequestBody UpdateProjectName updateProjectName, @AuthenticationPrincipal UserDetails userDetails) {

        String userId = userDetails.getUsername();
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

        ProjectDTO updatedProject = projectService.updateProjectName(updateProjectName, userId);

        ApiResponse<ProjectDTO> response = new ApiResponse<>(
                "Updated project name successfully",
                updatedProject
        );

        return ResponseEntity.ok(response);
    }


<<<<<<< HEAD

    @DeleteMapping("{projectId}")
    public ResponseEntity<ApiResponse<ProjectCreationResponse>> deleteProject(@PathVariable long projectId) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
=======
    //delete project each and every folder and files i have to delete

    @DeleteMapping("{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable long projectId,  @AuthenticationPrincipal UserDetails userDetails) {
        try {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

            String userId = userDetails.getUsername();

            ProjectDTO deletedProject = projectService.deleteProject(projectId, userId);

            ProjectCreationResponse projectCreationResponse = new ProjectCreationResponse(deletedProject);

            ApiResponse<ProjectCreationResponse> apiResponse = new ApiResponse<>(
                    "Project deleted successfully",
                    projectCreationResponse
            );
            return ResponseEntity.ok().body(apiResponse);
        } catch (Exception e) {
<<<<<<< HEAD
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            ApiResponse<ProjectCreationResponse> errorResponse = new ApiResponse<>(
                    "Failed to delete item",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
=======
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        }
    }
}
