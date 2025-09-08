package com.stackblitz.OnlineIDE.dto;


import lombok.Data;

@Data
public class UserResponse {
    private String firstName;
    private String email;

    private String token; // optional — can be null for signup

    public UserResponse(String firstName, String email, String token) {
        this.firstName = firstName;
        this.email = email;
        this.token = token;

    }


}
