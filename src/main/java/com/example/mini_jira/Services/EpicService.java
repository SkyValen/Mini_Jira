package com.example.mini_jira.Services;

import com.example.mini_jira.classes.Epic;
import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.User;
import com.example.mini_jira.repositories.EpicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpicService {
    private final EpicRepository epicRepository;
    private final ProjectService projectService;

    public EpicService (EpicRepository epicRepository, ProjectService projectService) {
        this.epicRepository = epicRepository;
        this.projectService = projectService;
    }

    public Epic createEpic(Epic epic, Long userId, Long projectId) {
        projectService.validateMembershipProject(userId, projectId);
        epic.setProject(project);
        return epicRepository.save(epic);
    }

    public Epic getEpicById(Long epicId) {
        return epicRepository.findById(epicId)
                .orElseThrow(() -> new RuntimeException("Epic not found"));
    }

    public List<Epic> getEpicsByProject(Project project, User user) {
        projectService.validateMembershipProject(user, project);
        return epicRepository.findByProject(project);
    }

    public Epic validateEpic(Long epicId, Long projectId) {
        Epic epic = getEpicById(epicId);
        Project project = projectService.getProjectById(projectId);
        if (!epic.getProject().getId().equals(project.getId())) {
            throw new RuntimeException("This epic does not belong to this project");
        }
        return epic;
    }

    public void deleteEpic(Long epicId, Long userId, Long projectId) {
        projectService.validateMembershipProject(userId, projectId);
        Epic epic = validateEpic(epicId, projectId);
        epicRepository.delete(epic);
    }
}
