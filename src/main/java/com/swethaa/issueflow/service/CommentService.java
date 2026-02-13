package com.swethaa.issueflow.service;

import com.swethaa.issueflow.dto.CommentRequest;
import com.swethaa.issueflow.entity.Issue;
import com.swethaa.issueflow.entity.User;
import com.swethaa.issueflow.entity.Comment;
import com.swethaa.issueflow.repository.IssueRepository;
import com.swethaa.issueflow.repository.UserRepository;
import com.swethaa.issueflow.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

import com.swethaa.issueflow.dto.CommentResponse;

import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, IssueRepository issueRepository, UserRepository userRepository){
        this.commentRepository = commentRepository;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public CommentResponse addComment(Long issueId, CommentRequest request, String authorEmail){
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));
        User author = userRepository.findByEmail(authorEmail).orElseThrow(() -> new IllegalArgumentException ("User not found"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setIssue(issue);
        comment.setAuthor(author);

        Comment saved = commentRepository.save(comment);

        return new CommentResponse(
                saved.getId(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getAuthor().getName(),
                saved.getAuthor().getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long issueId){
        System.out.println("SERVICE looking for issueId = " + issueId);
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));

        return commentRepository.findByIssueId(issueId)
                .stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getAuthor().getName(),
                        comment.getAuthor().getEmail()
                ))
                .collect(Collectors.toList());
    }

}
