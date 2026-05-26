package com.example.mini_jira.repositories;

import com.example.mini_jira.classes.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository <Task, Long> {
    List<Task> findByProject(Project project);
    List<Task> findByProjectAndAssignee(Project project, User assignee);
    List<Task> findByCreator(User creator);
    List<Task> findBySprint(Sprint sprint);
    List<Task> findByEpic(Epic epic);
    List<Task> findByStatus(Task.TaskStatus status);
    List<Task> findByProjectAndStatus(Project project, Task.TaskStatus status);
}
