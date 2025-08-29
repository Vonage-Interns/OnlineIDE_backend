package com.stackblitz.OnlineIDE.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.ProjectTreeResponse;
import com.stackblitz.OnlineIDE.dto.RunCodeRequest;
import com.stackblitz.OnlineIDE.dto.RunCodeResponse;
import com.stackblitz.OnlineIDE.exceptions.FileNotFoundException;
import com.stackblitz.OnlineIDE.model.Files;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.service.DockerCodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeExecutionControler {

    private final DockerCodeExecutionService service;
    private final FileRepo fileRepo;

    @PostMapping("/run")
    public ResponseEntity<ApiResponse<RunCodeResponse>> run(@RequestBody RunCodeRequest request) {
        try {

//            UserDetails userDetails = (UserDetails) SecurityContextHolder
//                    .getContext()
//                    .getAuthentication()
//                    .getPrincipal();
//
//            String userId = userDetails.getUsername();

            Long fileId = request.getFileId();
            String fileName = request.getFileName(); // example: "index.js"

            Files files = fileRepo.findById(fileId)
                    .orElseThrow(() -> new FileNotFoundException("File not found"));

            ObjectMapper mapper = new ObjectMapper();

            Map<String, String> codeMap = mapper.readValue(files.getContentJson(),
                    new TypeReference<Map<String, String>>() {});

            List<Integer> keys = codeMap.keySet().stream()
                    .map(Integer::parseInt)
                    .sorted()
                    .toList();

            StringBuilder codeBuilder = new StringBuilder();
            for (Integer key : keys) {
                codeBuilder.append(codeMap.get(String.valueOf(key))).append("\n");
            }

            String code = codeBuilder.toString();

            String language = "js";

            RunCodeResponse result = service.runCode(language, code);

            return ResponseEntity.ok(new ApiResponse("Code executed successfully", result));

        } catch (RuntimeException e) {
            if ("File not found".equals(e.getMessage())) {
                ApiResponse<RunCodeResponse> errorResponse = new ApiResponse<>(
                        "File not found",
                        null
                );
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
            ApiResponse<RunCodeResponse> errorResponse = new ApiResponse<>(
                    "Bad request",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        } catch (Exception e) {
            e.printStackTrace();
            ApiResponse<RunCodeResponse> errorResponse = new ApiResponse<>(
                    "Execution failed",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }
}

