package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.FolderDTO;
import com.stackblitz.OnlineIDE.dto.FolderResponseDTO;
import com.stackblitz.OnlineIDE.dto.FolderTreeDTO;
import com.stackblitz.OnlineIDE.dto.UpdateName;
import com.stackblitz.OnlineIDE.service.FoldersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FolderControllerTest {

    @InjectMocks
    private FolderController folderController;

    @Mock
    private FoldersService foldersService;

    @Mock
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createFolder_shouldReturnCreatedResponse_whenFolderCreatedSuccessfully() {
        String userID = "42";

        FolderDTO folderDTO = new FolderDTO();
        folderDTO.setName("TestFolder");
        folderDTO.setProjectId(1L);
        folderDTO.setParentFolderId(2L);

        FolderResponseDTO folderResponseDTO = FolderResponseDTO.builder()
                        .id(1L)
                        .name("TestFolder")
                        .projectId(1L)
                        .parentFolderId(2L)
                        .build();

        when(userDetails.getUsername()).thenReturn(userID);
        when(foldersService.createFolder(folderDTO, userID)).thenReturn(folderResponseDTO);

        ResponseEntity<?> response = folderController.createFolder(folderDTO, userDetails);

        assertEquals(201, response.getStatusCode().value());
        FolderResponseDTO responseBody = (FolderResponseDTO) response.getBody();
        assertEquals(folderResponseDTO, responseBody);

    }

    @Test
    void updateFolderName_shouldReturnOkResponse_whenFolderNameUpdatedSuccessfully() {
        String userID = "42";

        UpdateName updateName = UpdateName.builder()
                .folderId(1l)
                .updateName("UpdatedFolder")
                .build();

        FolderResponseDTO folderResponseDTO = FolderResponseDTO.builder()
                .id(1L)
                .name("TestFolder")
                .projectId(1L)
                .parentFolderId(2L)
                .build();

        when(userDetails.getUsername()).thenReturn(userID);
        when(foldersService.updateFolderName(updateName, userID)).thenReturn(folderResponseDTO);

        ResponseEntity<?> response = folderController.updateFolderName(updateName, userDetails);


        assertEquals(200, response.getStatusCode().value());
        FolderResponseDTO responseBody = (FolderResponseDTO) response.getBody();
        assertEquals(folderResponseDTO, responseBody);

    }

    @Test
    void deleteFolder_shouldReturnOkResponse_whenFolderDeletedSuccessfully() {
        long folderId = 1L;
        String userID = "42";

        FolderTreeDTO  folderTreeDTO = FolderTreeDTO.builder()
                .id(1L)
                .name("TestFolder")
                .build();


        when(userDetails.getUsername()).thenReturn(userID);
        when(foldersService.deleteFolder(folderId, userID)).thenReturn(folderTreeDTO);

        ResponseEntity<?> response = folderController.deleteFolder(folderId, userDetails);

        assertEquals(200, response.getStatusCode().value());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("Folder delete successfully", apiResponse.getMessage());
        assertEquals(folderTreeDTO, apiResponse.getData());

    }
}