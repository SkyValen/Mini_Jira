package com.example.mini_jira.repositories;

import com.example.mini_jira.classes.Epic;
import com.example.mini_jira.classes.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpicRepository extends JpaRepository <Epic, Long> {
    List<Epic> findByProject(Project project);
}
