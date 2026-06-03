package com.example.mini_jira.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {
    public enum TaskStatus {
        TODO,
        IN_PROGRESS,
        TESTING,
        DONE
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    private Integer storyPoints;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;
    @ManyToOne
    @JoinColumn(name = "epic_id")
    private Epic epic;
    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    public Task() {}

    public Task(String title, String description, Project project, User creator, Epic epic) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.status = TaskStatus.TODO;
        this.creator = creator;
        this.epic = epic;
    }

    //Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public Integer getStoryPoints() { return storyPoints; }
    public Project getProject() { return project; }
    public User getCreator() { return creator; }
    public User getAssignee() { return assignee; }
    public Epic getEpic() { return epic; }
    public Sprint getSprint() { return sprint; }
    //Setters
    public void setId(long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public void setStoryPoints(Integer storyPoints) { this.storyPoints = storyPoints; }
    public void setProject(Project project) { this.project = project; }
    public void setCreator(User creator) { this.creator = creator; }
    public void setAssignee(User assignee) { this.assignee = assignee; }
    public void setEpic(Epic epic) { this.epic = epic; }
    public void setSprint(Sprint sprint) { this.sprint = sprint; }
}
