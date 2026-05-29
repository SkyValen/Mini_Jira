package com.example.mini_jira.Services;

import com.example.mini_jira.classes.*;
import com.example.mini_jira.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final SprintService sprintService;
    private final EpicService epicService;
    private final UserService userService;

    public TaskService (TaskRepository taskRepository, ProjectService projectService, SprintService sprintService, EpicService epicService, UserService userService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.sprintService = sprintService;
        this.epicService = epicService;
        this.userService = userService;
    }

    public Task createTask(Task task, Long userId, Long projectId) {
        Project project = projectService.validateMembershipProject(userId, projectId);
        task.setProject(project);
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task validateTask(Long taskId, Long projectId) {
        Task task = getTaskById(taskId);
        Project project = projectService.getProjectById(projectId);
        if (!task.getProject().getId().equals(project.getId())){
            throw new RuntimeException("This task does not belong to this project");
        }
        return task;
    }

    public List<Task> getTasksByProject(Long projectId, Long userId) {
        Project project = projectService.validateMembershipProject(userId, projectId);
        return taskRepository.findByProject(project);
    }

    public List<Task> getTasksByAssignee(Long assigneeId, Long projectId, Long searcherId) {
        Project project = projectService.validateMembershipProject(searcherId, projectId);

        User assignee = projectService.validateMembershipUser(assigneeId, projectId);

        return taskRepository.findByProjectAndAssignee(project, assignee);
    }

    public Task changeStatus(Long taskId, Task.TaskStatus status, Long userId, Long projectId) {
        projectService.validateMembershipProject(userId, projectId);

        Task task = validateTask(taskId, projectId);

        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task assignUser(Long taskId, Long assigneeId, Long userId, Long projectId) {
        projectService.validateMembershipProject(userId, projectId);

        User assignee = projectService.validateMembershipUser(assigneeId, projectId);

        Task task = validateTask(taskId, projectId);

        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, Long userId, Long projectId) {
        projectService.validateMembershipProject(userId, projectId);
        Task task = validateTask(taskId, projectId);
        taskRepository.delete(task);
    }

    public Task addToSprint (Long taskId, Long userId, Long projectId, Long sprintId) {
        projectService.validateMembershipProject(userId, projectId);
        sprintService.validateSprint(sprintId, projectId);
        Task task = validateTask(taskId, projectId);
        if (task.getStatus() == Task.TaskStatus.DONE){
            throw new RuntimeException("Cannot assign task with status DONE");
        }
        Sprint sprint = sprintService.validateIfSprintActive(sprintId);
        task.setSprint(sprint);
        return taskRepository.save(task);
    }

    public Task removeFromSprint(Long taskId, Long userId, Long projectId) {
        projectService.validateMembershipProject(userId, projectId);
        Task task = validateTask(taskId, projectId);
        task.setSprint(null);
        return taskRepository.save(task);
    }

    public Task addToEpic(Long taskId, Long userId, Long projectId, Long epicId) {
        projectService.validateMembershipProject(userId, projectId);

        Epic epic = epicService.validateEpic(epicId, projectId);

        Task task = validateTask(taskId, projectId);

        task.setEpic(epic);
        return taskRepository.save(task);
    }

    public Task removeFromEpic(Long taskId, Long userId, Long projectId) {
        projectService.validateMembershipProject(userId, projectId);

        Task task = validateTask(taskId, projectId);

        task.setEpic(null);
        return taskRepository.save(task);
    }
}
