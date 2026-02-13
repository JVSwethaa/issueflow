package com.swethaa.issueflow.dto;

import java.time.LocalDateTime;

public class CommentResponse {

    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;
    private final String authorName;
    private final String authorEmail;

    public CommentResponse(
            Long id,
            String content,
            LocalDateTime createdAt,
            String authorName,
            String authorEmail) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }
}
