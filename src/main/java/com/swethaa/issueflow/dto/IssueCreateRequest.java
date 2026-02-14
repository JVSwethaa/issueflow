package com.swethaa.issueflow.dto;

import com.swethaa.issueflow.entity.Priority;

import jakarta.validation.constraints.*;

public class IssueCreateRequest {

    @NotBlank (message = "Title is required")
    private String title;

    @NotBlank (message = "Description is required")
    private String description;

    @NotNull (message = "Priority is required")
    private Priority priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
