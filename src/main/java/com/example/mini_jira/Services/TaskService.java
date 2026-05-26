package com.example.mini_jira.Services;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.Task;
import com.example.mini_jira.classes.User;
import com.example.mini_jira.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    public TaskService (TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    public Task createTask(Task task, User user, Project project) {
        projectService.validateMembership(user, project);
        task.setProject(project);
        return taskRepository.save(task);
    }

    public void checkIfTaskBelongToProject(Task task, Project project) {
        if (!task.getProject().getId().equals(project.getId())){
            throw new RuntimeException("This task does not belong to this project");
        }
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<Task> getTasksByProject(Project project, User user) {
        projectService.validateMembership(user, project);
        return taskRepository.findByProject(project);
    }

    public List<Task> getTasksByAssignee(User assignee, Project project, User searcher) {
        projectService.validateMembership(searcher, project);
        projectService.validateMembership(assignee, project);
        return taskRepository.findByProjectAndAssignee(project, assignee);
    }

    public Task changeStatus(Long taskId, Task.TaskStatus status, User user, Project project) {
        projectService.validateMembership(user, project);
        Task task = getTaskById(taskId);
        checkIfTaskBelongToProject(task, project);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task assignUser(Long taskId, User assignee, User user, Project project) {
        projectService.validateMembership(user, project);
        projectService.validateMembership(assignee, project);
        Task task = getTaskById(taskId);
        checkIfTaskBelongToProject(task, project);
        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, User user, Project project) {
        projectService.validateMembership(user, project);
        Task task = getTaskById(taskId);
        checkIfTaskBelongToProject(task, project);
        taskRepository.delete(task);
    }
}
