package com.example.mini_jira.classes;

import jakarta.persistence.*;

@Entity
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public ProjectMember () {}

    public ProjectMember (User user, Project project) {
        this.user = user;
        this.project = project;
    }

    //Getters
    public long getId() { return id; }
    public User getUser() { return user; }
    public Project getProject() { return project; }
    //Setters
    public void setId(long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setProject(Project project) { this.project = project; }
}
