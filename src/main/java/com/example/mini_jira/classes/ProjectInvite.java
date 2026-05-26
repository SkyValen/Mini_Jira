package com.example.mini_jira.classes;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ProjectInvite {
    public enum InviteStatus {
        IN_PROCESS,
        ACCEPTED,
        DENIED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "invited_id", nullable = false)
    private User invitedUser;
    @ManyToOne
    @JoinColumn(name = "inviter_id", nullable = false)
    private User invitedBy;
    @Enumerated(EnumType.STRING)
    private InviteStatus status = InviteStatus.IN_PROCESS;
    private LocalDateTime createdAt = LocalDateTime.now();

    public ProjectInvite() {}

    public ProjectInvite(Project project, User invitedUser, User invitedBy) {
        this.project = project;
        this.invitedUser = invitedUser;
        this.invitedBy = invitedBy;
    }

    //Getters
    public long getId() { return id; }
    public Project getProject() { return project; }
    public User getInvitedUser() { return invitedUser; }
    public User getInvitedBy() { return invitedBy; }
    public InviteStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    //Setters
    public void setId(long id) { this.id = id; }
    public void setProject(Project project) { this.project = project; }
    public void setInvitedUser(User invitedUser) { this.invitedUser = invitedUser; }
    public void setInvitedBy(User invitedBy) { this.invitedBy = invitedBy; }
    public void setStatus(InviteStatus status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
