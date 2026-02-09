package com.swethaa.issueflow.dto;

import com.swethaa.issueflow.entity.IssueStatus;

public class IssueStatusUpdateRequest {
    private IssueStatus status;

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }
}
