package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.ProjectService;
import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.ProjectMember;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //Project related
    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestBody Project project,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(project, userId));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> findProjectById(
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(projectService.validateMembershipProject(userId, projectId));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getUserProjects(
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(projectService.getUserProjects(userId));
    }

    @PostMapping("/{projectId}/user/{addedUserId}")
    public ResponseEntity<ProjectMember> addUserToProject(
            @PathVariable Long projectId,
            @PathVariable Long addedUserId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.addUserToProject(userId, addedUserId, projectId));
    }
}
