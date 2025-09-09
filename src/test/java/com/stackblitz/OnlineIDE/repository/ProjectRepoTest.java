package com.stackblitz.OnlineIDE.repository;

import com.stackblitz.OnlineIDE.model.Project;
import com.stackblitz.OnlineIDE.model.Users;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.globally_quoted_identifiers=true"
})
class ProjectRepoTest {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    private Users saveduser;

    @BeforeEach
    void setUp() {
        Users user = new Users();
        user.setFirstName("shreeshail");
        user.setLastName("patil");
        user.setEmail("shreeshail@gmail.com");
        user.setPassword("password@123");
        saveduser = userRepo.save(user);

        Project project1 = new Project();
        project1.setName("Test Project1");
        project1.setUser(saveduser);
        project1.setCreated_at(LocalDateTime.now().withNano(0));
        project1.setUpdated_at(LocalDateTime.now().withNano(0));
        project1.setKey("key1");
        projectRepo.save(project1);

        Project project2 = new Project();
        project2.setName("Test Project2");
        project2.setUser(saveduser);
        project2.setCreated_at(LocalDateTime.now().withNano(0));
        project2.setUpdated_at(LocalDateTime.now().withNano(0));
        project2.setKey("key2");
        projectRepo.save(project2);
    }

    @AfterEach
    void tearDown() {
        projectRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    void findByUser() {
        List<Project> projects = projectRepo.findByUser(saveduser);
        assertEquals(2, projects.size());
        assertTrue(
                projects.stream().anyMatch(p -> p.getName().equals("Test Project1")) &&
                        projects.stream().anyMatch(p -> p.getName().equals("Test Project2"))
        );
    }
}
