package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.service.FileService;
import com.stackblitz.OnlineIDE.service.JWTservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService filesService;

    private final JWTservice jwTservice;

    @GetMapping("/{id}")
    public ResponseEntity<?> getFile(@PathVariable Long id,  @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String userId = userDetails.getUsername();
            FileResponseDTO file = filesService.getFileById(id, userId);

            ApiResponse response = new ApiResponse("File fetched", file);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch file");
        }
    }

    @PostMapping
    public ResponseEntity<?> createFile(@RequestBody FileDTO request,  @AuthenticationPrincipal UserDetails userDetails) {
        try {

            String userId = userDetails.getUsername();
            FileResponseDTO file = filesService.createFile(request, userId);


            ApiResponse response = new ApiResponse("File created successfully", file);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e)   {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create file");
        }
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable long fileId) {
        try {

           FileTreeDTO fileTreeDTO =  filesService.deleteFile(fileId);

            ApiResponse response = new ApiResponse("File deleted successfully", fileTreeDTO);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error deleting file", e.getMessage()));
        }
    }

    @PutMapping("/{fileId}/content")
    public ResponseEntity<?> updateFileContent(@PathVariable long fileId,
                                               @RequestBody UpdateFileContentRequest Keyandcontent){
        try {
            FileTreeDTO fileTreeDTO = filesService.updateFileContent(fileId, Keyandcontent);
            ApiResponse response = new ApiResponse("File Updated successfully", fileTreeDTO);

            return ResponseEntity.ok(response);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping("/fileNameUpdate")
    public  ResponseEntity<ApiResponse> updateFileName(@RequestBody FileNameUpdateDTO fileNameUpdateDTO){
        try{
            FileNameUpdateDTO updatedFile = filesService.UpdateFileName(fileNameUpdateDTO);

            ApiResponse apiResponse = new ApiResponse("File updated was successful", updatedFile);

            return ResponseEntity.ok((apiResponse));
        }catch (Exception e){
            ApiResponse apiResponse = new ApiResponse("File updated was unsuccessful " + e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
        }
    }
}



