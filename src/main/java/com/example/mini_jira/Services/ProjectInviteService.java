package com.example.mini_jira.Services;

import com.example.mini_jira.classes.Project;
import com.example.mini_jira.classes.ProjectInvite;
import com.example.mini_jira.classes.User;
import com.example.mini_jira.repositories.ProjectInviteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInviteService {
    private final ProjectInviteRepository inviteRepository;
    private final ProjectService projectService;
    private final UserService userService;

    public ProjectInviteService (ProjectInviteRepository inviteRepository, ProjectService projectService,UserService userService) {
        this.inviteRepository = inviteRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    public ProjectInvite createInvite(Long projectId, Long inviterId, Long invitedUserId){
        Project project = projectService.validateMembershipProject(inviterId, projectId);

        if (projectService.validateMembershipUser(invitedUserId, projectId) != null) {
            throw new RuntimeException("User already in project");
        }

        User invitedUser = userService.getUserById(invitedUserId);
        User inviter = userService.getUserById(inviterId);
        inviteRepository.findByProjectAndInvitedUserAndStatus(project, invitedUser, ProjectInvite.InviteStatus.IN_PROCESS)
                .ifPresent(invite -> {
                    throw new RuntimeException("User already invited");
                });

        ProjectInvite invite = new ProjectInvite(project, invitedUser, inviter);

        return inviteRepository.save(invite);
    }

    public List<ProjectInvite> getUserInvites(Long userId) {
        User user = userService.getUserById(userId);
        return inviteRepository.findByInvitedUser(user);
    }

    public List<ProjectInvite> getProjectInvites(Long projectId, Long userId){
        Project project = projectService.validateMembershipProject(userId, projectId);
        return inviteRepository.findByProject(project);
    }

    public ProjectInvite acceptInvite(Long inviteId, Long userId) {
        ProjectInvite invite = validateInvite(inviteId, userId);

        if (invite.getStatus() != ProjectInvite.InviteStatus.IN_PROCESS){
            throw new RuntimeException("Cannot change decision");
        }

        invite.setStatus(ProjectInvite.InviteStatus.ACCEPTED);

        projectService.addUserToProject(invite.getInvitedBy().getId(), userId, invite.getProject().getId());

        return inviteRepository.save(invite);
    }

    public ProjectInvite denyInvite(Long inviteId, Long userId) {
        ProjectInvite invite = validateInvite(inviteId, userId);

        if (invite.getStatus() != ProjectInvite.InviteStatus.IN_PROCESS){
            throw new RuntimeException("Cannot change decision");
        }

        invite.setStatus(ProjectInvite.InviteStatus.DENIED);

        return inviteRepository.save(invite);
    }

    public ProjectInvite validateInvite(Long inviteId, Long userId) {
        ProjectInvite invite = getInviteById(inviteId);
        if (!invite.getInvitedUser().getId().equals(userId)){
            throw new RuntimeException("This invite do not belong to this user");
        }
        return invite;
    }

    public ProjectInvite getInviteById(Long inviteId) {
        return inviteRepository.findById(inviteId)
                .orElseThrow(() -> new RuntimeException("Invite not found"));
    }
}
