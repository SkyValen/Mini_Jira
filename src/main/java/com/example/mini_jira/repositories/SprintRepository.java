package com.example.mini_jira.repositories;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SprintRepository extends JpaRepository <Sprint, Long>{
    List<Sprint> findByProject(Project project);
    boolean existsByProjectAndActive(Project project, boolean active);
    Optional<Sprint> findByProjectAndActive(Project project, boolean active);
}
