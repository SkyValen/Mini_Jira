package com.example.mini_jira.Services;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.ProjectMember;
import com.example.mini_jira.classes.User;
import com.example.mini_jira.repositories.ProjectMemberRepository;
import com.example.mini_jira.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public ProjectService (ProjectRepository projectRepository, ProjectMemberRepository projectMemberRepository) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    public Project createProject(Project project) {
        Project savedProject = projectRepository.save(project);

        ProjectMember member =
                new ProjectMember(project.getOwner(), savedProject);

        projectMemberRepository.save(member);

        return savedProject;
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public List<Project> getUserProjects(User user) {
        List<ProjectMember> membership =
                projectMemberRepository.findByUser(user);
        return membership.stream()
                .map(ProjectMember::getProject)
                .toList();
    }

    public ProjectMember addUserToProject(User user, Project project) {
        if (projectMemberRepository.existsByUserAndProject(user, project)) {
            throw new RuntimeException("User Already in project");
        }

        ProjectMember member = new ProjectMember(user, project);

        return projectMemberRepository.save(member);
    }

    public void validateMembership(User user, Project project) {
        if (!projectMemberRepository.existsByUserAndProject(user, project)){
            throw new RuntimeException("Access Denied");
        };
    }
}
