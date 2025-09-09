package com.stackblitz.OnlineIDE.repository;

import com.stackblitz.OnlineIDE.model.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    private Users savedUser;

    @BeforeEach
    void setUp() {
        Users user = new Users();
        user.setFirstName("shreeshail");
        user.setLastName("patil");
        user.setEmail("shreeshail@gmail.com");
        user.setPassword("password@123");
        savedUser = userRepo.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    @Test
    void findByEmail_shouldReturnUser_whenUserExists() {
        // when
        Optional<Users> foundUser = userRepo.findByEmail("shreeshail@gmail.com");

        // then
        assertTrue(foundUser.isPresent()); //Verifies that the Optional is not empty.
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getFirstName()).isEqualTo("shreeshail");
        assertThat(foundUser.get().getLastName()).isEqualTo("patil");
        assertThat(foundUser.get().getEmail()).isEqualTo("shreeshail@gmail.com");
    }

    //negative test case to check it should return empty optional
    @Test
    void findByEmail_shouldReturnEmpty_whenUserDoesNotExist() {
        // when
        Optional<Users> foundUser = userRepo.findByEmail("notfound@gmail.com");

        // then
        assertFalse(foundUser.isPresent());
    }
}
