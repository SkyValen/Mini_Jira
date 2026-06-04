package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.EpicService;
import com.example.mini_jira.Services.ProjectService;
import com.example.mini_jira.classes.Epic;
import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.Sprint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epic")
public class EpicController {
    private final EpicService epicService;
    private final ProjectService projectService;

    public EpicController(EpicService epicService, ProjectService projectService) {
        this.epicService = epicService;
        this.projectService = projectService;
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Epic> createEpic(
            @RequestBody Epic epic,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.status(HttpStatus.CREATED).body(epicService.createEpic(epic, userId, projectId));
    }

    @GetMapping("/{epicId}/project/{projectId}")
    public ResponseEntity<Epic> getEpicById(
            @PathVariable Long epicId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        projectService.validateMembershipProject(userId, projectId);
        return ResponseEntity.ok(epicService.validateEpic(epicId, projectId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Epic>> getEpicsByProject(
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        projectService.validateMembershipProject(userId, projectId);
        return ResponseEntity.ok(epicService.getEpicsByProject(projectId, userId));
    }

    @GetMapping("/project/{projectId}/sprint/{sprintId}")
    public ResponseEntity<List<Epic>> getEpicsBySprint(
            @PathVariable Long projectId,
            @PathVariable Long sprintId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        projectService.validateMembershipProject(userId, projectId);
        return ResponseEntity.ok(epicService.getEpicsBySprint(sprintId, projectId));
    }

    @DeleteMapping("/{epicId}/project/{projectId}")
    public ResponseEntity<Void> deleteEpic(
            @PathVariable Long epicId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        epicService.deleteEpic(epicId, userId, projectId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{epicId}/project/{projectId}/sprint/{sprintId}")
    public ResponseEntity<Epic> assignEpicToSprint(
            @PathVariable Long epicId,
            @PathVariable Long projectId,
            @PathVariable Long sprintId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(epicService.addToSprint(sprintId, userId, projectId, epicId));
    }
}
