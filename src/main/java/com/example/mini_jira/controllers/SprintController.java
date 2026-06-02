package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.SprintService;
import com.example.mini_jira.classes.Sprint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sprint")
public class SprintController {
    private final SprintService sprintService;

    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Sprint> startNewSprint(
            @RequestBody Sprint sprint,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.status(HttpStatus.CREATED).body(sprintService.startSprint(sprint, userId, projectId));
    }

    @GetMapping("/project/{projectId}/active")
    public ResponseEntity<Sprint> getActiveSprint(
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(sprintService.getActiveSprint(userId, projectId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Sprint>> getSprintByProject(
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(sprintService.getProjectSprints(userId, projectId));
    }

    @PatchMapping("/project/{projectId}")
    public ResponseEntity<Sprint> endActiveSprint(
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(sprintService.endActiveSprint(userId, projectId));
    }
}
