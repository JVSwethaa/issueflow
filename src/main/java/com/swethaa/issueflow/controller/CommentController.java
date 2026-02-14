package com.swethaa.issueflow.controller;

import com.swethaa.issueflow.dto.CommentRequest;
import com.swethaa.issueflow.entity.Comment;
import com.swethaa.issueflow.service.CommentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.List;

import com.swethaa.issueflow.dto.CommentResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/issues/{issueId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@PathVariable("issueId") Long issueId, @Valid @RequestBody CommentRequest commentRequest, Authentication authentication){
        String authorEmail = authentication.getName();
        CommentResponse created = commentService.addComment(issueId, commentRequest, authorEmail);


        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable("issueId") Long issueId){
        List<CommentResponse> comments = commentService.getComments(issueId);

        return ResponseEntity.ok(comments);
    }
}
