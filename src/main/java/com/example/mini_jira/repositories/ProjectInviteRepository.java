package com.example.mini_jira.repositories;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.ProjectInvite;
import com.example.mini_jira.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectInviteRepository extends JpaRepository <ProjectInvite, Long> {
    List<ProjectInvite> findByInvitedUser(User invitedUser);
    List<ProjectInvite> findByProject(Project project);
    Optional<ProjectInvite> findByProjectAndInvitedUserAndStatus(Project project, User invitedUser, ProjectInvite.InviteStatus status);
}
