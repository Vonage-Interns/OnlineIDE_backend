package com.stackblitz.OnlineIDE.repository;

import com.stackblitz.OnlineIDE.model.Project;
import com.stackblitz.OnlineIDE.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {
    List<Project> findByUser(Users user);

}
