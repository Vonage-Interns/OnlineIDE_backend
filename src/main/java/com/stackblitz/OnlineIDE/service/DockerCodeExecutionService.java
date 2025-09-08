package com.stackblitz.OnlineIDE.service;

import com.stackblitz.OnlineIDE.dto.RunCodeResponse;
import com.stackblitz.OnlineIDE.exceptions.UnsupportLanguageException;
<<<<<<< HEAD
import com.stackblitz.OnlineIDE.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class DockerCodeExecutionService {

    private final UserRepo UserRepo;

    public RunCodeResponse runCode(String language, String code) {
=======
import org.springframework.stereotype.Service;

@Service
public class DockerCodeExecutionService {
    public  RunCodeResponse runCode(String language, String code) {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        try {
            String image;
            String runCommand;

            switch (language.toLowerCase()) {
                case "js", "javascript" -> {
                    image = "node:20";
<<<<<<< HEAD
                    // echo "console.log('Hello')" | node
=======
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
                    String escapedCode = code.replace("\"", "\\\"").replace("\n", "\\n");
                    runCommand = "echo \"" + escapedCode + "\" | node";
                }
                default -> throw new UnsupportLanguageException("Unsupported language: " + language);
            }
<<<<<<< HEAD
            //what is processBuilder ?
=======
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
            //It's a workflow tool that allows your system admin to create new processes without writing code,
            // and creates shortcuts for users to accomplish repetitive and time consuming tasks with the click of a button

            ProcessBuilder pb = new ProcessBuilder(
                    "docker", "run", "--rm",
                    image, "sh", "-c", runCommand
            );

            Process process = pb.start();
            String output = new String(process.getInputStream().readAllBytes());
            String error = new String(process.getErrorStream().readAllBytes());
            int exitCode = process.waitFor();

            return new RunCodeResponse(output, error, exitCode);

        } catch (Exception e) {
            return new RunCodeResponse("", e.getMessage(), 1);
        }
    }
}
