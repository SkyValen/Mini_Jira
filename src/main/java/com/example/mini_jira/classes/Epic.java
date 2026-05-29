package com.example.mini_jira.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "epics")
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    public Epic() {}

    public Epic(String title, String description, Project project) {
        this.title = title;
        this.description = description;
        this.project = project;
    }

    //Getters
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Project getProject() { return project; }
    public Sprint getSprint() { return sprint; }
    //Setters
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setProject(Project project) { this.project = project; }
    public void setSprint(Sprint sprint) { this.sprint = sprint; }
}
