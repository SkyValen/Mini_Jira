package com.example.mini_jira.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Comment() {}

    public Comment(String text, User author, Task task) {
        this.text = text;
        this.author = author;
        this.task = task;
    }

    //Getters
    public long getId() { return id; }
    public String getText() { return text; }
    public User getAuthor() { return author; }
    public Task getTask() { return task; }
    //Setters
    public void setId(long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
    public void setAuthor(User author) { this.author = author; }
    public void setTask(Task task) { this.task = task; }
}
