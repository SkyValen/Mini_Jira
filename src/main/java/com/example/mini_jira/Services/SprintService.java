package com.example.mini_jira.Services;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.Sprint;
import com.example.mini_jira.classes.User;
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

    public Sprint getActiveSprint(Project project, User user){
        projectService.validateMembership(user, project);
        return sprintRepository.findByProjectAndActive(project, true)
                .orElseThrow(() -> new RuntimeException("No active sprint"));
    }

    public Sprint createSprint(Sprint sprint, User user, Project project) {
        projectService.validateMembership(user, project);
        if (sprintRepository.existsByProjectAndActive(project, true)){
            throw new RuntimeException("There is currently active sprint in the project");
        }
        sprint.setProject(project);
        return sprintRepository.save(sprint);
    }

    public List<Sprint> getProjectSprints(Project project, User user) {
        projectService.validateMembership(user, project);
        return sprintRepository.findByProject(project);
    }

    public Sprint startSprint(Long sprintId, Project project, User user){
        projectService.validateMembership(user, project);

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new RuntimeException("Could not find a sprint"));

        if (!sprint.getProject().getId().equals(project.getId())){
            throw new RuntimeException("You cannot start sprint in this project");
        }

        sprintRepository.findByProjectAndActive(project, true)
                .ifPresent(old -> {
                    old.setActive(false);
                    sprintRepository.save(old);
                });

        sprint.setActive(true);

        return sprintRepository.save(sprint);
    }

    public void endActiveSprint(Project project, User user) {
        projectService.validateMembership(user, project);

        Sprint sprint = sprintRepository.findByProjectAndActive(project, true)
                .orElseThrow(() -> new RuntimeException("Could not find an active sprint"));

        sprint.setActive(false);
        sprintRepository.save(sprint);
    }
}
