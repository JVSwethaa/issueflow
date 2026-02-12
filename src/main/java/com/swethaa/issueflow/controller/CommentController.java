package com.swethaa.issueflow.controller;

import com.swethaa.issueflow.dto.CommentRequest;
import com.swethaa.issueflow.entity.Comment;
import com.swethaa.issueflow.service.CommentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.List;

@RestController
@RequestMapping("/issues/{issueId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> addComment(@PathVariable("issueId") Long issueId, @RequestBody CommentRequest commentRequest, Authentication authentication){
        String authorEmail = authentication.getName();
        Comment created = commentService.addComment(issueId, commentRequest, authorEmail);


        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable("issueId") Long issueId){
        return ResponseEntity.ok(commentService.getComments(issueId));
    }
}
