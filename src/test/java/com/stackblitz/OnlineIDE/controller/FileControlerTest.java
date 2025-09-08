package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.*;
import com.stackblitz.OnlineIDE.exceptions.FileNotFoundException;
import com.stackblitz.OnlineIDE.service.FileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileControlerTest {

    @InjectMocks
    private FileControler fileControler;

    @Mock
    private FileService fileService;

    @Mock
    private UserDetails userDetails;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getFile() {
        Long fileId = 1L;
        String userId = "42";

        FileResponseDTO fileResponseDTO = FileResponseDTO.builder()
                .id(fileId)
                .name("test.js")
                .type("js")
                .content("{\"1\":\"console.log('Hello World');\"}")
                .build();

        when(userDetails.getUsername()).thenReturn(userId);
        when(fileService.getFileById(fileId, userId)).thenReturn(fileResponseDTO);

        ResponseEntity<?> response = fileControler.getFile(fileId, userDetails);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("File fetched", apiResponse.getMessage());
        assertEquals(fileResponseDTO, apiResponse.getData());

    }

    @Test
    void getFile_shouldReturnErrorResponse_WhenExceptionThrown() {
        long fileId = 1L;
        String userId = "42";

        when(userDetails.getUsername()).thenReturn(userId);
        when(fileService.getFileById(fileId, userId)).thenThrow(new FileNotFoundException("File not Found"));

        ResponseEntity<?> response = fileControler.getFile(fileId, userDetails);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Failed to fetch file", response.getBody());
    }

    @Test
    void createFile() {
        String userID = "42";

        FileDTO fileDTO = FileDTO.builder()
                .fileName("testfile.js")
                .type("js")
                .content(Map.of("1", "console.log('Test');"))
                .projectID(100L)
                .folderID(200L)
                .build();

        FileResponseDTO fileResponseDTO = FileResponseDTO.builder()
                .id(2L)
                .name("testfile.js")
                .type("js")
                .content("{\"1\":\"console.log('Test');\"}")
                .build();

        when(userDetails.getUsername()).thenReturn(userID);
        when(fileService.createFile(fileDTO, userID)).thenReturn(fileResponseDTO);

        ResponseEntity<?> response = fileControler.createFile(fileDTO, userDetails);
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("File created successfully", apiResponse.getMessage());
        assertEquals(fileResponseDTO, apiResponse.getData());

    }

    @Test
    void deleteFile_shouldReturnOkResponse_whenFileDeletedSuccessfully(){
        long fileId = 1L;

        FileTreeDTO fileTreeDTO = new FileTreeDTO();
        fileTreeDTO.setId(1L);
        fileTreeDTO.setType("js");
        fileTreeDTO.setName("testfile.js");

        when(fileService.deleteFile(fileId)).thenReturn(fileTreeDTO);
        ResponseEntity<?> response = fileControler.deleteFile(fileId);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("File deleted successfully", apiResponse.getMessage());
        assertEquals(fileTreeDTO, apiResponse.getData());

    }

    @Test
    void updateFileContent_shouldReturnOkResponse_whenContentUpdatedSuccessfully() {
        long fileId = 1L;
        UpdateFileContentRequest request = new UpdateFileContentRequest(Map.of("1", "console.log('Updated');"));

        FileTreeDTO fileTreeDTO = FileTreeDTO.builder()
                .id(fileId)
                .name("testfile.js")
                .type("js")
                .build();

        when(fileService.updateFileContent(fileId, request)).thenReturn(fileTreeDTO);

        ResponseEntity<?> response = fileControler.updateFileContent(fileId, request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        assertEquals("File Updated successfully", apiResponse.getMessage());
        assertEquals(fileTreeDTO, apiResponse.getData());
    }

    @Test
    void updateFileName_shouldReturnOkResponse_whenFileNameUpdatedSuccessfully() {
        FileNameUpdateDTO fileNameUpdateDTO = new FileNameUpdateDTO();
        fileNameUpdateDTO.setFileId(1L);
        fileNameUpdateDTO.setFileName("updatedName.js");

        when(fileService.UpdateFileName(fileNameUpdateDTO)).thenReturn(fileNameUpdateDTO);

        ResponseEntity<ApiResponse> response = fileControler.updateFileName(fileNameUpdateDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        ApiResponse apiResponse = response.getBody();
        assertEquals("File updated was successful", apiResponse.getMessage());
        assertEquals(fileNameUpdateDTO, apiResponse.getData());
    }
}