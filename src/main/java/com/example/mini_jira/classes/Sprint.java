package com.example.mini_jira.classes;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "sprints")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    private boolean active;

    public Sprint() {}

    public Sprint(String name, LocalDate startDate, LocalDate endDate, Project project) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project = project;
        this.active = false;
    }

    //Getters
    public long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public Project getProject() { return project; }
    public boolean isActive() { return active; }
    //Setters
    public void setId(long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setProject(Project project) { this.project = project; }
    public void setActive(boolean active) { this.active = active; }
}
