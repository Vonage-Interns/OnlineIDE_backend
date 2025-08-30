package com.stackblitz.OnlineIDE.service;


import com.stackblitz.OnlineIDE.exceptions.UserNotFoundException;
import com.stackblitz.OnlineIDE.model.Users;
import com.stackblitz.OnlineIDE.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepository;

    private final PasswordEncoder passwordEncoder;

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Users signUp(Users user) {
        // isPresent method return True if email already exist else False
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        //encrypt the password
        user.setPassword((passwordEncoder.encode(user.getPassword())));
        return userRepository.save(user);

    }

}
