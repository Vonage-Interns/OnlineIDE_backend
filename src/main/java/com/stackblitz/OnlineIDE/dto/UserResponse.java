package com.stackblitz.OnlineIDE.dto;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
@JsonInclude(JsonInclude.Include.NON_NULL)
=======
import lombok.Data;

>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
@Data
public class UserResponse {
    private String firstName;
    private String email;
<<<<<<< HEAD
    private String token;

    public UserResponse(String firstName) {
        this.firstName = firstName;
=======
    private String token; // optional — can be null for signup

    public UserResponse(String firstName, String email, String token) {
        this.firstName = firstName;
        this.email = email;
        this.token = token;
>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
    }

    public UserResponse(String firstName, String token) {
        this.firstName = firstName;
        this.token = token;
    }
<<<<<<< HEAD
=======


>>>>>>> a472369 (code-update: added unit testing controller and repo layer)
}
