package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.service.FoldersService;
import com.stackblitz.OnlineIDE.service.JWTservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/folders")
@RequiredArgsConstructor
@CrossOrigin
public class FolderController {

    private final FoldersService folderService;

    private final JWTservice jwtService;

    @PostMapping
    public ResponseEntity<FolderResponseDTO> createFolder(

            @RequestBody FolderDTO folderDTO,
            @AuthenticationPrincipal UserDetails userDetails) {


        String userId = userDetails.getUsername();

        FolderResponseDTO created = folderService.createFolder(folderDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PatchMapping("/rename")
    public ResponseEntity<FolderResponseDTO> updateFolderName(
            @RequestBody UpdateName updateName,
            @AuthenticationPrincipal UserDetails userDetails) {


        String userId = userDetails.getUsername();

        FolderResponseDTO updated = folderService.updateFolderName(updateName, userId);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{folderId}")

    public ResponseEntity<?> deleteFolder(@PathVariable long folderId,  @AuthenticationPrincipal UserDetails userDetails){
        try {


            String userId = userDetails.getUsername();

            FolderTreeDTO folderTreeDTO =  folderService.deleteFolder(folderId, userId);

            ApiResponse apiResponse = new ApiResponse(
                    "Folder delete successfully",
                    folderTreeDTO
            );
            return ResponseEntity.ok(apiResponse);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }
    }
}
