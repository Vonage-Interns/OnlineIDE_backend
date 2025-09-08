package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
<<<<<<< HEAD
import com.stackblitz.OnlineIDE.dto.*;
=======
import com.stackblitz.OnlineIDE.dto.FolderDTO;
import com.stackblitz.OnlineIDE.dto.FolderResponseDTO;
import com.stackblitz.OnlineIDE.dto.FolderTreeDTO;
import com.stackblitz.OnlineIDE.dto.UpdateName;
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
import com.stackblitz.OnlineIDE.service.FoldersService;
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
@RequestMapping("/api/folders")
@RequiredArgsConstructor
@CrossOrigin
public class FolderControler {

    private final FoldersService folderService;

    private final JWTservice jwtService;

    @PostMapping
    public ResponseEntity<FolderResponseDTO> createFolder(
<<<<<<< HEAD
            @RequestBody FolderDTO folderDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
=======
            @RequestBody FolderDTO folderDTO,
            @AuthenticationPrincipal UserDetails userDetails) {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

        String userId = userDetails.getUsername();

        FolderResponseDTO created = folderService.createFolder(folderDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

<<<<<<< HEAD

    @PatchMapping("/rename")
    public ResponseEntity<FolderResponseDTO> updateFolderName(
            @RequestBody UpdateName updateName) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
=======
    @PatchMapping("/rename")
    public ResponseEntity<FolderResponseDTO> updateFolderName(
            @RequestBody UpdateName updateName,
            @AuthenticationPrincipal UserDetails userDetails) {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

        String userId = userDetails.getUsername();

        FolderResponseDTO updated = folderService.updateFolderName(updateName, userId);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{folderId}")
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<FolderTreeDTO>> deleteFolder(@PathVariable long folderId){
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();
=======
    public ResponseEntity<?> deleteFolder(@PathVariable long folderId,  @AuthenticationPrincipal UserDetails userDetails){
        try {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)

            String userId = userDetails.getUsername();

            FolderTreeDTO folderTreeDTO =  folderService.deleteFolder(folderId, userId);
<<<<<<< HEAD
            ApiResponse<FolderTreeDTO> apiResponse = new ApiResponse(
=======
            ApiResponse apiResponse = new ApiResponse(
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
                    "Folder delete succesfully",
                    folderTreeDTO
            );
            return ResponseEntity.ok(apiResponse);

        }catch (Exception e){
<<<<<<< HEAD
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            ApiResponse<FolderTreeDTO> errorResponse = new ApiResponse<>(
                    "Failed to delete Folder",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
=======
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        }
    }
}
