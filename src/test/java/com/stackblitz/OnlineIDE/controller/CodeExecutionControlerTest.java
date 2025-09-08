// src/test/java/com/stackblitz/OnlineIDE/controller/CodeExecutionControlerTest.java
package com.stackblitz.OnlineIDE.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.RunCodeRequest;
import com.stackblitz.OnlineIDE.dto.RunCodeResponse;
import com.stackblitz.OnlineIDE.model.Files;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.service.DockerCodeExecutionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodeExecutionControlerTest {

    @Mock
    private DockerCodeExecutionService dockerCodeExecutionService;

    @Mock
    private FileRepo fileRepo;

    @InjectMocks
    private CodeExecutionControler codeExecutionControler;

    @Test
    void run_success() throws Exception {
        Long fileId = 1L;
        String fileName = "main.js";
        String language = "js";
        String contentJson = new ObjectMapper().writeValueAsString(Map.of("1", "console.log('Hello World');"));

        Files files = Files.builder()
                .id(fileId)
                .name(fileName)
                .type(language)
                .contentJson(contentJson)
                .build();

        when(fileRepo.findById(fileId)).thenReturn(Optional.of(files));
        RunCodeResponse runCodeResponse = new RunCodeResponse("Hello World", "", 0);
        when(dockerCodeExecutionService.runCode(eq(language), anyString())).thenReturn(runCodeResponse);

        RunCodeRequest request = new RunCodeRequest(fileId, fileName);
        ResponseEntity<?> response = codeExecutionControler.run(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        ApiResponse apiResponse = (ApiResponse) response.getBody();
        RunCodeResponse result = (RunCodeResponse) apiResponse.getData();

        assertEquals("Hello World", result.getOutput());
        assertEquals(0, result.getExitCode());
    }
}