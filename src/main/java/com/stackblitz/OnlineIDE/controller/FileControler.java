package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.service.FileService;
import com.stackblitz.OnlineIDE.service.JWTservice;
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

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileControler {

    private final FileService filesService;

    private final JWTservice jwTservice;

    @GetMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<FileResponseDTO>> getFile(@PathVariable Long id) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            String userId = userDetails.getUsername();
            FileResponseDTO file = filesService.getFileById(id, userId);

            ApiResponse<FileResponseDTO> response = new ApiResponse("File fetched", file);
=======
    public ResponseEntity<?> getFile(@PathVariable Long id,  @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String userId = userDetails.getUsername();
            FileResponseDTO file = filesService.getFileById(id, userId);

            ApiResponse response = new ApiResponse("File fetched", file);
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
<<<<<<< HEAD
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch file");
            ApiResponse<FileResponseDTO> errorResponse = new ApiResponse<>(
                    "Failed to Fetch file",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
=======
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch file");
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        }
    }

    @PostMapping
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<FileResponseDTO>> createFile(@RequestBody FileDTO request) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
=======
    public ResponseEntity<?> createFile(@RequestBody FileDTO request,  @AuthenticationPrincipal UserDetails userDetails) {
        try {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

            String userId = userDetails.getUsername();
            FileResponseDTO file = filesService.createFile(request, userId);

<<<<<<< HEAD
            ApiResponse<FileResponseDTO> response = new ApiResponse("File created successfully", file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create file");
            ApiResponse<FileResponseDTO> errorResponse = new ApiResponse<>(
                    "Failed to create file",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
=======
            ApiResponse response = new ApiResponse("File created successfully", file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e)   {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create file");
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        }
    }

    @DeleteMapping("/{fileId}")
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<FileTreeDTO>> deleteFile(@PathVariable long fileId) {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            String userId = userDetails.getUsername();

           FileTreeDTO fileTreeDTO =  filesService.deleteFile(fileId, userId);

            ApiResponse<FileTreeDTO> response = new ApiResponse("File deleted successfully", fileTreeDTO);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error deleting file", e.getMessage()));
            ApiResponse<FileTreeDTO> errorResponse = new ApiResponse<>(
                    "Failed to delete file",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
=======
    public ResponseEntity<?> deleteFile(@PathVariable long fileId) {
        try {

           FileTreeDTO fileTreeDTO =  filesService.deleteFile(fileId);

            ApiResponse response = new ApiResponse("File deleted successfully", fileTreeDTO);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error deleting file", e.getMessage()));
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        }
    }

    @PutMapping("/{fileId}/content")
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<FileTreeDTO>> updateFileContent(@PathVariable long fileId,
                                               @RequestBody UpdateFileContentRequest Keyandcontent){
        try {

            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            String userId = userDetails.getUsername();

            FileTreeDTO fileTreeDTO = filesService.updateFileContent(fileId, Keyandcontent, userId);
            ApiResponse<FileTreeDTO> response = new ApiResponse("File Updated successfully", fileTreeDTO);
=======
    public ResponseEntity<?> updateFileContent(@PathVariable long fileId,
                                               @RequestBody UpdateFileContentRequest Keyandcontent){
        try {
            FileTreeDTO fileTreeDTO = filesService.updateFileContent(fileId, Keyandcontent);
            ApiResponse response = new ApiResponse("File Updated successfully", fileTreeDTO);
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

            return ResponseEntity.ok(response);

        }catch (Exception e){
<<<<<<< HEAD
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            ApiResponse<FileTreeDTO> errorResponse = new ApiResponse<>(
                    "Failed to update file content",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


=======
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
    @PatchMapping("/fileNameUpdate")
    public  ResponseEntity<ApiResponse> updateFileName(@RequestBody FileNameUpdateDTO fileNameUpdateDTO){

        try{
<<<<<<< HEAD

            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            String userId = userDetails.getUsername();

            FileNameUpdateDTO updatedFile = filesService.UpdateFileName(fileNameUpdateDTO, userId);
=======
            FileNameUpdateDTO updatedFile = filesService.UpdateFileName(fileNameUpdateDTO);
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

            ApiResponse apiResponse = new ApiResponse("File updated was successful", updatedFile);

            return ResponseEntity.ok((apiResponse));
        }catch (Exception e){
            ApiResponse apiResponse = new ApiResponse("File updated was unsuccessful " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}



