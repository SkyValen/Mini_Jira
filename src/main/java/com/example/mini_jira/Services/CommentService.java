package com.example.mini_jira.Services;

import com.example.mini_jira.classes.Comment;
import com.example.mini_jira.classes.Task;
import com.example.mini_jira.classes.User;
import com.example.mini_jira.repositories.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;

    public CommentService(CommentRepository commentRepository, ProjectService projectSerivce, UserService userService, TaskService taskService){
        this.commentRepository = commentRepository;
        this.projectService = projectSerivce;
        this.userService = userService;
        this.taskService = taskService;
    }

    public Comment createComment(
            String text,
            Long taskId,
            Long userId,
            Long projectId
    ) {
        User author = projectService.validateMembershipUser(userId, projectId);
        Task task = taskService.validateTask(taskId, projectId);

        Comment comment = new Comment(text, author, task);

        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    public List<Comment> getTaskComments(
            Long taskId,
            Long userId,
            Long projectId
    ) {
        projectService.validateMembershipProject(userId, projectId);
        Task task = taskService.validateTask(taskId, projectId);

        return commentRepository.findByTask(task);
    }

    public void deleteComment(
            Long commentId,
            Long userId,
            Long projectId
    ) {
        projectService.validateMembershipProject(userId, projectId);
        Comment comment = validateComment(userId, commentId, projectId);

        commentRepository.delete(comment);
    }

    public Comment validateComment(Long authorId, Long commentId, Long projectId){
        Comment comment = getCommentById(commentId);

        if (!comment.getTask().getProject().getId().equals(projectId)){
            throw new RuntimeException("This comment does not belong to the project");
        } else if (!comment.getAuthor().getId().equals(authorId)) {
            throw new RuntimeException("Only author can modify comment");
        }

        return comment;
    }
}
