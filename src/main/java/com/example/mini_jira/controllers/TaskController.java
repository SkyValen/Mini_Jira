package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.ProjectService;
import com.example.mini_jira.Services.TaskService;
import com.example.mini_jira.classes.Task;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    private final ProjectService projectService;

    public TaskController(TaskService taskService, ProjectService projectService) {
        this.taskService = taskService;
        this.projectService = projectService;
    }

    //Task related
    @PostMapping("/project/{projectId}")
    public ResponseEntity<Task> createTask(
            @RequestBody Task task,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task, userId, projectId));
    }

    @GetMapping("/{taskId}/project/{projectId}")
    public ResponseEntity<Task> getTaskById(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ){
        Long userId = (Long) request.getAttribute("id");
        projectService.validateMembershipProject(userId, projectId);
        return ResponseEntity.ok(taskService.validateTask(taskId, projectId));
    }

    @DeleteMapping("/{taskId}/project/{projectId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        taskService.deleteTask(taskId, userId, projectId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.getTasksByProject(projectId, userId));
    }

    @PatchMapping("/{taskId}/project/{projectId}/status/{status}")
    public ResponseEntity<Task> changeStatus(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            @PathVariable Task.TaskStatus status,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.changeStatus(taskId, status, userId, projectId));
    }



    //Epic related
    @GetMapping("/project/{projectId}/epic/{epicId}")
    public ResponseEntity<List<Task>> getTasksByEpic(
            @PathVariable Long epicId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.getTasksByEpic(epicId, userId, projectId));
    }

    @PatchMapping("/{taskId}/project/{projectId}/epic/{epicId}")
    public ResponseEntity<Task> addToEpic(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            @PathVariable Long epicId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.addToEpic(taskId, userId, projectId, epicId));
    }

    @DeleteMapping("/{taskId}/project/{projectId}/epic")
    public ResponseEntity<Task> removeFromEpic(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.removeFromEpic(taskId, userId, projectId));
    }



    //Assignee related
    @GetMapping("/project/{projectId}/assignee/{assigneeId}")
    public ResponseEntity<List<Task>> getTasksByAssignee(
            @PathVariable Long projectId,
            @PathVariable Long assigneeId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.getTasksByAssignee(assigneeId, projectId, userId));
    }

    @PatchMapping("/{taskId}/project/{projectId}/assignee/{assigneeId}")
    public ResponseEntity<Task> assignUserToTask(
            @PathVariable Long taskId,
            @PathVariable Long assigneeId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.assignUser(taskId, assigneeId, userId, projectId));
    }

    @DeleteMapping("/{taskId}/project/{projectId}/assignee")
    public ResponseEntity<Task> unassignUser(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.unassignUser(taskId, userId, projectId));
    }



    //Sprint related
    @PatchMapping("/{taskId}/project/{projectId}/sprint/{sprintId}")
    public ResponseEntity<Task> addToSprint(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            @PathVariable Long sprintId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.addToSprint(taskId, userId, projectId, sprintId));
    }

    @DeleteMapping("/{taskId}/project/{projectId}/sprint")
    public ResponseEntity<Task> removeFromSprint(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");
        return ResponseEntity.ok(taskService.removeFromSprint(taskId, userId, projectId));
    }
}
