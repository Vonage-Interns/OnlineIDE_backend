package com.stackblitz.OnlineIDE.exceptions;


import com.stackblitz.OnlineIDE.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // make class globally avaliable
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ApiResponse> handleGeneralException(Exception ex) {
        ApiResponse<?> response = new ApiResponse<>("Internal server error" + ex.getMessage(), null);
        return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR)).body(response);
    }

    @ExceptionHandler(DuplicateProjectException.class)
    public ResponseEntity<ApiResponse> handleDuplicateProjectException(DuplicateProjectException ex) {
        ApiResponse<?> apiResponse = new ApiResponse(ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ApiResponse> handleProjectNotFoundException(ProjectNotFoundException ex){
        ApiResponse<?>  apiResponse = new ApiResponse(ex.getMessage(),  null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(FolderNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFolderNotFoundException(FolderNotFoundException ex){
        ApiResponse<?>  apiResponse = new ApiResponse(ex.getMessage(),  null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFileNotFoundException(FileNotFoundException ex){
        ApiResponse<?>  apiResponse = new ApiResponse(ex.getMessage(),  null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFoundException(UserNotFoundException ex){
        ApiResponse<?>  apiResponse = new ApiResponse(ex.getMessage(),  null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(UnsupportLanguageException.class)
    public ResponseEntity<ApiResponse> handleUnsupportLanguageException(UnsupportLanguageException ex){
        ApiResponse<?>  apiResponse = new ApiResponse(ex.getMessage(),  null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }













}
