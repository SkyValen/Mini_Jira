package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.ProjectInviteService;
import com.example.mini_jira.Services.UserService;
import com.example.mini_jira.classes.ProjectInvite;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invite")
public class ProjectInviteController {

    private final ProjectInviteService projectInviteService;
    private final UserService userService;

    public ProjectInviteController(ProjectInviteService projectInviteService, UserService userService) {
        this.projectInviteService = projectInviteService;
        this.userService = userService;
    }

    @PostMapping("/project/{projectId}/user/{username}")
    public ResponseEntity<ProjectInvite> createInvite(
            @PathVariable Long projectId,
            @PathVariable String username,
            HttpServletRequest request
    ) {
        Long inviterId = (Long) request.getAttribute("id");
        Long userId = userService.getByUsername(username).getId();

        return ResponseEntity.status(HttpStatus.CREATED).body(projectInviteService.createInvite(projectId, inviterId, userId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProjectInvite>> getUserInvites(
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");

        return ResponseEntity.ok(projectInviteService.getUserInvites(userId));
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectInvite>> getInvitesToProject(
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(projectInviteService.getProjectInvites(projectId, userId));
    }

    @PatchMapping("/{inviteId}/accept")
    public ResponseEntity<ProjectInvite> acceptInvite(
            @PathVariable Long inviteId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(projectInviteService.acceptInvite(inviteId, userId));
    }

    @PatchMapping("/{inviteId}/deny")
    public ResponseEntity<ProjectInvite> denyInvite(
            @PathVariable Long inviteId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(projectInviteService.denyInvite(inviteId, userId));
    }
}
