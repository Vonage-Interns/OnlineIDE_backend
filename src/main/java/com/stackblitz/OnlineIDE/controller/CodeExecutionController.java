package com.stackblitz.OnlineIDE.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.RunCodeRequest;
import com.stackblitz.OnlineIDE.dto.RunCodeResponse;
import com.stackblitz.OnlineIDE.exceptions.FileNotFoundException;
import com.stackblitz.OnlineIDE.model.Files;
import com.stackblitz.OnlineIDE.repository.FileRepo;
import com.stackblitz.OnlineIDE.service.DockerCodeExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class CodeExecutionController {

    private final DockerCodeExecutionService service;
    private final FileRepo fileRepo;

    @PostMapping("/run")

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

            List<Integer> keys = codeMap.keySet().stream()
                    .map(Integer::parseInt)
                    .sorted()
                    .toList();


            // 3. Build final code string

            StringBuilder codeBuilder = new StringBuilder();
            for (Integer key : keys) {
                codeBuilder.append(codeMap.get(String.valueOf(key))).append("\n");
            }



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

}

