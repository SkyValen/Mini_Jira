package com.example.mini_jira.classes;

import jakarta.persistence.*;

@Entity
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

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
    //Setters
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setProject(Project project) { this.project = project; }
}
