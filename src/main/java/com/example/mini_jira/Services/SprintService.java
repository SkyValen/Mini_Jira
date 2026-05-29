package com.example.mini_jira.Services;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.Sprint;
import com.example.mini_jira.repositories.SprintRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {
    private final SprintRepository sprintRepository;
    private final ProjectService projectService;

    public SprintService(SprintRepository sprintRepository, ProjectService projectService) {
        this.sprintRepository = sprintRepository;
        this.projectService = projectService;
    }

    public Sprint createSprint(Sprint sprint, Long userId, Long projectId) {
        Project project = projectService.validateMembershipProject(userId, projectId);
        if (sprintRepository.existsByProjectAndActive(project, true)) {
            throw new RuntimeException("There is currently active sprint in the project");
        }
        sprint.setProject(project);
        return sprintRepository.save(sprint);
    }

    public Sprint getSprintById(Long sprintId) {
        return sprintRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Could not find a sprint"));
    }

    public Sprint getActiveSprint(Long userId, Long projectId){
        Project project = projectService.validateMembershipProject(userId, projectId);
        return sprintRepository.findByProjectAndActive(project, true)
                .orElseThrow(() -> new RuntimeException("No active sprint"));
    }

    public List<Sprint> getProjectSprints( Long userId, Long projectId) {
        projectService.validateMembershipProject(userId, projectId);
        Project project = projectService.getProjectById(projectId);
        return sprintRepository.findByProject(project);
    }

    public Sprint startSprint(Long sprintId, Long userId, Long projectId){
        Project project = projectService.validateMembershipProject(userId, projectId);
        Sprint sprint = validateSprint(sprintId, projectId);

        sprintRepository.findByProjectAndActive(project, true)
                .ifPresent(old -> {
                    old.setActive(false);
                    sprintRepository.save(old);
                });

        sprint.setActive(true);

        return sprintRepository.save(sprint);
    }

    public void endActiveSprint(Long userId, Long projectId) {
        Project project = projectService.validateMembershipProject(userId, projectId);
        Sprint sprint = sprintRepository.findByProjectAndActive(project, true)
                .orElseThrow(() -> new RuntimeException("Could not find an active sprint"));

        sprint.setActive(false);
        sprintRepository.save(sprint);
    }

    public Sprint validateSprint(Long sprintId, Long projectId) {
        Sprint sprint = getSprintById(sprintId);
        Project project = projectService.getProjectById(projectId);
        if (!sprint.getProject().getId().equals(project.getId())) {
            throw new RuntimeException("This sprint does not belong to this project");
        }
        return sprint;
    }

    public Sprint validateIfSprintActive(Long sprintId) {
        Sprint sprint = getSprintById(sprintId);
        if (!sprint.isActive()) {
            throw new RuntimeException("Sprint is not active");
        }
        return sprint;
    }
}
