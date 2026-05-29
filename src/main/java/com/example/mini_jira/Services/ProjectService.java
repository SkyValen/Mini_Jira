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
    private final UserService userService;

    public ProjectService (ProjectRepository projectRepository, ProjectMemberRepository projectMemberRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.userService = userService;
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

    public ProjectMember addUserToProject(Long userId, Long addedUserId, Long projectId) {
        validateMembershipProject(userId, projectId);
        validateMembershipProject(addedUserId, projectId);
        User addedUser = userService.getUserById(addedUserId);
        Project project = getProjectById(projectId);
        if (projectMemberRepository.existsByUserAndProject(addedUser, project)) {
            throw new RuntimeException("User Already in project");
        }

        ProjectMember member = new ProjectMember(addedUser, project);

        return projectMemberRepository.save(member);
    }

    public Project validateMembershipProject(Long userId, Long projectId) {
        User user = userService.getUserById(userId);
        Project project = getProjectById(projectId);
        if (!projectMemberRepository.existsByUserAndProject(user, project)){
            throw new RuntimeException("Access Denied");
        };
        return project;
    }

    public User validateMembershipUser(Long userId, Long projectId) {
        User user = userService.getUserById(userId);
        Project project = getProjectById(projectId);
        if (!projectMemberRepository.existsByUserAndProject(user, project)){
            throw new RuntimeException("Access Denied");
        };
        return user;
    }
}
