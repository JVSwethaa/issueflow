package com.swethaa.issueflow.dto;

import com.swethaa.issueflow.entity.IssueStatus;
import com.swethaa.issueflow.entity.Priority;


public class IssueUpdateRequest {

    private String title;
    private String description;
    private IssueStatus status;
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

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
