package com.example.mini_jira.controllers;

import com.example.mini_jira.Services.CommentService;
import com.example.mini_jira.classes.Comment;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping("/task/{taskId}/project/{projectId}")
    public ResponseEntity<Comment> createComment(
            @RequestBody String text,
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(text, taskId, userId, projectId));
    }

    @GetMapping("/task/{taskId}/project/{projectId}")
    public ResponseEntity<List<Comment>> getCommentsByTask(
            @PathVariable Long taskId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");

        return ResponseEntity.ok(commentService.getTaskComments(taskId, userId, projectId));
    }

    @DeleteMapping("/{commentId}/project/{projectId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @PathVariable Long projectId,
            HttpServletRequest request
    ) {
        Long userId = (Long) request.getAttribute("id");

        commentService.deleteComment(commentId, userId, projectId);

        return ResponseEntity.noContent().build();
    }
}
