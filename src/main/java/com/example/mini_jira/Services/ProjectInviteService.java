package com.example.mini_jira.Services;

import com.example.mini_jira.classes.ProjectInvite;
import com.example.mini_jira.repositories.ProjectInviteRepository;
import com.example.mini_jira.repositories.ProjectMemberRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectInviteService {
    private final ProjectInviteRepository inviteRepository;
    private final ProjectMemberRepository memberRepository;

    public ProjectInviteService (ProjectInviteRepository inviteRepository, ProjectMemberRepository memberRepository) {
        this.inviteRepository = inviteRepository;
        this.memberRepository = memberRepository;
    }

    public ProjectInvite sendInvite(ProjectInvite invite) {

        invite.setStatus(ProjectInvite.InviteStatus.IN_PROCESS);
    }
}
