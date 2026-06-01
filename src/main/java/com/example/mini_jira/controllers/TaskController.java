package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.TaskService;
import com.example.mini_jira.classes.Task;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Task> createTask(@RequestBody Task task, HttpServletRequest request, @PathVariable Long projectId) {
        Long userId = (Long) request.getAttribute("id");

        Task createdTask = taskService.createTask(task, userId, projectId);

        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId){
        Task foundTask = taskService.getTaskById(taskId);
        return ResponseEntity.ok(foundTask);
    }

    @GetMapping("/project/{projectId}/all")
    public ResponseEntity<List<Task>> getAllByProject(@PathVariable Long projectId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        List<Task> listOfTasks = taskService.getTasksByProject(projectId, userId);
        return ResponseEntity.ok(listOfTasks);
    }

    @GetMapping("/project/{projectId}/epic/{epicId}/all")
    public ResponseEntity<List<Task>> getAllByEpic(@PathVariable Long epicId, @PathVariable Long projectId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        List<Task> listOfTasks = taskService.getTasksByEpic(epicId, userId, projectId);
        return ResponseEntity.ok(listOfTasks);
    }

    @GetMapping("/project/{projectId}/assignee/{assigneeId}/all")
    public ResponseEntity<List<Task>> getAllByAssignee(@PathVariable Long projectId, @PathVariable Long assigneeId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        List<Task> listOfTasks = taskService.getTasksByAssignee(assigneeId, projectId, userId);
        return ResponseEntity.ok(listOfTasks);
    }

    @PatchMapping("/{taskId}/project/{projectId}/status/{status}")
    public ResponseEntity<Task> changeStatus(@PathVariable Long taskId, @PathVariable Long projectId, @PathVariable Task.TaskStatus status, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Task updatedTask = taskService.changeStatus(taskId, status, userId, projectId);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/project/{projectId}/assignee/{assigneeId}")
    public ResponseEntity<Task> assignUser(@PathVariable Long taskId, @PathVariable Long assigneeId, @PathVariable Long projectId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Task updatedTask = taskService.assignUser(taskId, assigneeId, userId, projectId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{taskId}/project/{projectId}")
    public void deleteTask(@PathVariable Long taskId, @PathVariable Long projectId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        taskService.deleteTask(taskId, userId, projectId);
    }

    @PatchMapping("/{taskId}/project/{projectId}/sprint/{sprintId}")
    public ResponseEntity<Task> addToSprint(@PathVariable Long taskId, @PathVariable Long projectId, @PathVariable Long sprintId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Task updatedTask = taskService.addToSprint(taskId, userId, projectId, sprintId);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/project/{projectId}/sprint")
    public ResponseEntity<Task> removeFromSprint(@PathVariable Long taskId, @PathVariable Long projectId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Task updatedTask = taskService.removeFromSprint(taskId, userId, projectId);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/project/{projectId}/epic/{epicId}")
    public ResponseEntity<Task> addToEpic(@PathVariable Long taskId, @PathVariable Long projectId, @PathVariable Long epicId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Task updatedTask = taskService.addToEpic(taskId, userId, projectId, epicId);
        return ResponseEntity.ok(updatedTask);
    }

    @PatchMapping("/{taskId}/project/{projectId}/epic")
    public ResponseEntity<Task> removeFromEpic(@PathVariable Long taskId, @PathVariable Long projectId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("id");
        Task updatedTask = taskService.removeFromEpic(taskId, userId, projectId);
        return ResponseEntity.ok(updatedTask);
    }
}
