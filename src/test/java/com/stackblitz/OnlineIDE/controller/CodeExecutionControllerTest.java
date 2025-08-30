package com.stackblitz.OnlineIDE.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.RunCodeRequest;
import com.stackblitz.OnlineIDE.dto.RunCodeResponse;
import com.stackblitz.OnlineIDE.exceptions.FileNotFoundException;
import com.stackblitz.OnlineIDE.model.Files;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.service.DockerCodeExecutionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CodeExecutionControllerTest {

    @Mock
    private DockerCodeExecutionService dockerCodeExecutionService;

    @Mock
    private FileRepo fileRepo;

    @InjectMocks
    private CodeExecutionControler controller;

    private Files sampleFile;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        Map<String, String> contentMap = Map.of(
                "1", "console.log('Hello');",
                "2", "console.log('World');"
        );
        String jsonContent = new ObjectMapper().writeValueAsString(contentMap);

        sampleFile = new Files();
        sampleFile.setId(4L);
        sampleFile.setContentJson(jsonContent);
    }

    @Test
    void shouldRunCodeSuccessfully() {
        // Given
        RunCodeRequest request = new RunCodeRequest(4L, "index.js");
        when(fileRepo.findById(4L)).thenReturn(Optional.of(sampleFile));

        RunCodeResponse mockResponse = new RunCodeResponse("Hello\nWorld\n", "", 0);
        when(dockerCodeExecutionService.runCode(eq("js"), anyString()))
                .thenReturn(mockResponse);

        // When
        ResponseEntity<ApiResponse<RunCodeResponse>> responseEntity = controller.run(request);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Code executed successfully", responseEntity.getBody().getMessage());
        assertEquals(mockResponse, responseEntity.getBody().getData());

        verify(fileRepo, times(1)).findById(4L);
        verify(dockerCodeExecutionService, times(1)).runCode(eq("js"), anyString());
    }

    @Test
    void shouldReturnFileNotFound() {
        // Given
        RunCodeRequest request = new RunCodeRequest(999L, "index.js");
        when(fileRepo.findById(999L)).thenThrow(new FileNotFoundException("File not found"));

        // When
        ResponseEntity<ApiResponse<RunCodeResponse>> responseEntity = controller.run(request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("File not found", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());

        verify(fileRepo, times(1)).findById(999L);
        verifyNoInteractions(dockerCodeExecutionService);
    }

    @Test
    void shouldHandleOtherRuntimeException() {
        // Given
        RunCodeRequest request = new RunCodeRequest(4L, "index.js");
        when(fileRepo.findById(4L)).thenThrow(new RuntimeException("Some runtime error"));

        // When
        ResponseEntity<ApiResponse<RunCodeResponse>> responseEntity = controller.run(request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Bad request", responseEntity.getBody().getMessage());
        assertNull(responseEntity.getBody().getData());

        verify(fileRepo, times(1)).findById(4L);
        verifyNoInteractions(dockerCodeExecutionService);
    }

//    @Test
//    void shouldHandleGenericExceptionDuringExecution() {
//        // Given
//        RunCodeRequest request = new RunCodeRequest(4L, "index.js");
//        when(fileRepo.findById(4L)).thenReturn(Optional.of(sampleFile));
//        when(dockerCodeExecutionService.runCode(eq("js"), anyString()))
//                .thenThrow(new RuntimeException("Docker error"));
//
//        // When
//        ResponseEntity<ApiResponse<RunCodeResponse>> responseEntity = controller.run(request);
//
//        // Then
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
//        assertEquals("Execution failed", responseEntity.getBody().getMessage());
//        assertNull(responseEntity.getBody().getData());
//
//        verify(fileRepo, times(1)).findById(4L);
//        verify(dockerCodeExecutionService, times(1)).runCode(eq("js"), anyString());
//    }
}
