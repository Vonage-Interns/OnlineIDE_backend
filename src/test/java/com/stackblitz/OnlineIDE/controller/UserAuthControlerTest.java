package com.stackblitz.OnlineIDE.controller;

import com.stackblitz.OnlineIDE.common.ApiResponse;
import com.stackblitz.OnlineIDE.dto.UserResponse;
import com.stackblitz.OnlineIDE.dto.UserSignupResponseDTO;
import com.stackblitz.OnlineIDE.model.Users;
import com.stackblitz.OnlineIDE.service.JWTservice;
import com.stackblitz.OnlineIDE.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserAuthControlerTest {

    @Mock
    private UserService userService;

    @Mock
    private JWTservice jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserAuthControler userAuthControler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testSignUp_EmailAlreadyExists() {
        Users user = new Users();
        user.setEmail("existing@example.com");

        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        when(userService.signUp(user)).thenThrow(new RuntimeException("Email already registered"));

        ResponseEntity<ApiResponse<UserSignupResponseDTO>> response = userAuthControler.signUp(user, mockBindingResult);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Registration failed: Email already registered", response.getBody().getMessage());
    }

    @Test
    void testSignIn_Success() {
        Users user = new Users();
        user.setEmail("john@example.com");
        user.setPassword("password123");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        Users dbUser = new Users();
        dbUser.setFirstName("John");
        dbUser.setEmail("john@example.com");
        dbUser.setId(1L);

        when(userService.getUserByEmail("john@example.com")).thenReturn(dbUser);
        when(jwtService.generateToken(dbUser)).thenReturn("mocked-jwt-token");

        ResponseEntity<ApiResponse<UserResponse>> response = userAuthControler.signin(user);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Login successful", response.getBody().getMessage());
        assertEquals("John", response.getBody().getData().getFirstName());
        assertEquals("mocked-jwt-token", response.getBody().getData().getToken());
    }

    @Test
    void testSignIn_InvalidCredentials() {
        Users user = new Users();
        user.setEmail("john@example.com");
        user.setPassword("wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        ResponseEntity<ApiResponse<UserResponse>> response = userAuthControler.signin(user);

        assertEquals(401, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Invalid email or password", response.getBody().getMessage());
    }

    @Test
    void testSignIn_MissingEmailOrPassword() {
        Users user = new Users();
        user.setEmail("");
        user.setPassword("");

        ResponseEntity<ApiResponse<UserResponse>> response = userAuthControler.signin(user);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Email or password is missing", response.getBody().getMessage());
    }
}