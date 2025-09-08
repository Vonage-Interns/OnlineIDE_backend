package com.stackblitz.OnlineIDE.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackblitz.OnlineIDE.common.ApiResponse;
<<<<<<< HEAD
import com.stackblitz.OnlineIDE.dto.ProjectTreeResponse;
=======
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
import com.stackblitz.OnlineIDE.dto.RunCodeRequest;
import com.stackblitz.OnlineIDE.dto.RunCodeResponse;
import com.stackblitz.OnlineIDE.exceptions.FileNotFoundException;
import com.stackblitz.OnlineIDE.model.Files;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.service.DockerCodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
=======
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
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
<<<<<<< HEAD
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

=======
    public ResponseEntity<?> run(@RequestBody RunCodeRequest request) {
        try {
            Long fileId = request.getFileId();
            String fileName = request.getFileName(); // example: "index.js"

            // 1. Get file from repo
            Files files = fileRepo.findById(fileId)
                    .orElseThrow(() -> new FileNotFoundException("File not found"));

            // 2. Fetch file content (code)
            //String code = files.getContentJson(); //content in json format
            ObjectMapper mapper = new ObjectMapper();

            // 1. Convert JSON into Map
            Map<String, String> codeMap = mapper.readValue(files.getContentJson(),
                    new TypeReference<Map<String, String>>() {});

            // 2. Sort by line number (keys are strings, so parse to int)
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
            List<Integer> keys = codeMap.keySet().stream()
                    .map(Integer::parseInt)
                    .sorted()
                    .toList();

<<<<<<< HEAD
=======
            // 3. Build final code string
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
            StringBuilder codeBuilder = new StringBuilder();
            for (Integer key : keys) {
                codeBuilder.append(codeMap.get(String.valueOf(key))).append("\n");
            }

<<<<<<< HEAD
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
=======

            String code = codeBuilder.toString();


            // 3. Get the file type from db
            String language = files.getType();

            // 4. Execute code
            RunCodeResponse result = service.runCode(language, code);

            // 5. Return response
            return ResponseEntity.ok(new ApiResponse("Code executed successfully", result));


        } catch (RuntimeException e) {
            if ("File not found".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse("File not found", null));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Bad request: " + e.getMessage(), null));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Execution failed", null));
        }
    }



>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
}

