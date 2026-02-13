package com.swethaa.issueflow.dto;

import com.swethaa.issueflow.entity.IssueStatus;
import com.swethaa.issueflow.entity.Priority;
import java.time.LocalDateTime;

public class IssueDetailResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final IssueStatus status;
    private final Priority priority;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private final String reporterName;
    private final String reporterEmail;

    private final String assigneeName;
    private final String assigneeEmail;

    public IssueDetailResponse(
            Long id,
            String title,
            String description,
            IssueStatus status,
            Priority priority,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String reporterName,
            String reporterEmail,
            String assigneeName,
            String assigneeEmail) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reporterName = reporterName;
        this.reporterEmail = reporterEmail;
        this.assigneeName = assigneeName;
        this.assigneeEmail = assigneeEmail;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }
}
