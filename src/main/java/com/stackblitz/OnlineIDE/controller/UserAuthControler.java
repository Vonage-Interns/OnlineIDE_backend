package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.UserResponse;
<<<<<<< HEAD
=======
import com.stackblitz.OnlineIDE.dto.UserSignupResponseDTO;
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
import com.stackblitz.OnlineIDE.model.Users;
import com.stackblitz.OnlineIDE.service.JWTservice;
import com.stackblitz.OnlineIDE.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserAuthControler {

    private final UserService userService;

    private final JWTservice jwtService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
<<<<<<< HEAD
    public ResponseEntity<ApiResponse<UserResponse>> signUp(@Valid @RequestBody Users user, BindingResult result) {
=======
    public ResponseEntity<ApiResponse<UserSignupResponseDTO>> signUp(@Valid @RequestBody Users user, BindingResult result) {
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(errorMessage, null));
        }

        try {
            Users savedUser = userService.signUp(user);

<<<<<<< HEAD
            UserResponse userResponse = new UserResponse(
                    savedUser.getFirstName()
            );

            ApiResponse<UserResponse> response = new ApiResponse<>(
                    "Registration successful",
                    userResponse
=======
            UserSignupResponseDTO userSignupResponseDTO = new UserSignupResponseDTO();
            userSignupResponseDTO.setFirstName(savedUser.getFirstName());


            ApiResponse<UserSignupResponseDTO> response = new ApiResponse<>(
                    "Registration successful",
                    userSignupResponseDTO
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
<<<<<<< HEAD
            ApiResponse<UserResponse> errorResponse = new ApiResponse<>(
=======
            ApiResponse<UserSignupResponseDTO> errorResponse = new ApiResponse<>(
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
                    "Registration failed: " + e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


@PostMapping("/signin")
public ResponseEntity<ApiResponse<UserResponse>> signin(@RequestBody Users user) {
    try {

        // Null or blank checks
        if (user.getEmail() == null || user.getPassword() == null ||
                user.getEmail().trim().isEmpty() || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Email or password is missing", null));
        }

        //authentication.isAuthenticated() return true/false
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            // Fetch user from DB to get full info
            Users dbUser = userService.getUserByEmail(user.getEmail());

            //  Generate token based on db user
            String jwt = jwtService.generateToken(dbUser);

            // Build response with proper name
            UserResponse userData = new UserResponse(
                    dbUser.getFirstName(),
<<<<<<< HEAD
=======
                    null,
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
                    jwt
            );
            ApiResponse<UserResponse> response = new ApiResponse<>(
                    "Login successful",
                    userData
            );
            return ResponseEntity.ok(response);
        }

    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>("Invalid email or password", null));
    }catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("Login error: " + e.getMessage(), null));
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ApiResponse<>("Login failed", null));
}

}