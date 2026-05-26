package com.example.mini_jira.repositories;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.ProjectMember;
import com.example.mini_jira.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository <ProjectMember, Long> {
    List<ProjectMember> findByUser(User user);
    List<ProjectMember> findByProject(Project project);
    Optional<ProjectMember> findByUserAndProject(User user, Project project);
    boolean existsByUserAndProject(User user, Project project);
}
