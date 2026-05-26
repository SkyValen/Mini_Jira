package com.example.mini_jira.repositories;

import com.example.mini_jira.classes.Comment;
import com.example.mini_jira.classes.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {
    List<Comment> findByTask(Task task);
}
