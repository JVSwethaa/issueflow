package com.swethaa.issueflow.dto;

import jakarta.validation.constraints.*;

public class CommentRequest {

    @NotBlank (message = "Content is required")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
