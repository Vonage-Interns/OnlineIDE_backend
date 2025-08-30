package com.stackblitz.OnlineIDE.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserResponse {
    private String firstName;
    private String email;
    private String token;

    public UserResponse(String firstName) {
        this.firstName = firstName;
    }

    public UserResponse(String firstName, String token) {
        this.firstName = firstName;
        this.token = token;
    }
}
