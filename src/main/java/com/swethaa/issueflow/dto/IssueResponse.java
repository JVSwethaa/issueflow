package com.swethaa.issueflow.dto;

import com.swethaa.issueflow.entity.IssueStatus;
import com.swethaa.issueflow.entity.Priority;

import java.time.LocalDateTime;

public class IssueResponse {

    private final Long id;
    private final String title;
    private final IssueStatus status;
    private final Priority priority;
    private final LocalDateTime createdAt;
    private final String reporterName;
    private final String assigneeName;

    public IssueResponse(Long id,
                         String title,
                         IssueStatus status,
                         Priority priority,
                         LocalDateTime createdAt,
                         String reporterName,
                         String assigneeName){
        this.id = id;
        this.title = title;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
        this.reporterName = reporterName;
        this.assigneeName = assigneeName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public String getReporterName() {
        return reporterName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }
}
