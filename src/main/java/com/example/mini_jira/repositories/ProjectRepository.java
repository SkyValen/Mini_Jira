package com.example.mini_jira.repositories;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User owner);
}
