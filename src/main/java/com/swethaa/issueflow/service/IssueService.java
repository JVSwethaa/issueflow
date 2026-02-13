package com.swethaa.issueflow.service;

import com.swethaa.issueflow.entity.User;
import com.swethaa.issueflow.entity.Issue;
import com.swethaa.issueflow.dto.IssueCreateRequest;
import com.swethaa.issueflow.repository.IssueRepository;
import com.swethaa.issueflow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import com.swethaa.issueflow.dto.IssueUpdateRequest;
import com.swethaa.issueflow.entity.IssueStatus;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.swethaa.issueflow.entity.Priority;

import com.swethaa.issueflow.dto.IssueResponse;
import com.swethaa.issueflow.dto.IssueDetailResponse;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public IssueService(IssueRepository issueRepository, UserRepository userRepository){
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public IssueResponse createIssue(IssueCreateRequest request, String reporterEmail){
        User reporter = userRepository.findByEmail(reporterEmail).orElseThrow(() -> new IllegalArgumentException("Reporter not found"));

        Issue issue = new Issue();
        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        issue.setPriority(request.getPriority());
        issue.setReporter(reporter);

        Issue saved = issueRepository.save(issue);

        return new IssueResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getStatus(),
                saved.getPriority(),
                saved.getCreatedAt(),
                saved.getReporter().getName(),
                saved.getAssignee() != null ? saved.getAssignee().getName() : null
        );
    }

    @Transactional(readOnly = true)
    public Page<IssueResponse> getAllIssues(Pageable pageable){
        Page<Issue> issues = issueRepository.findAll(pageable);

        if(issues.isEmpty()){
            throw new IllegalArgumentException("No issues found");
        }
        return issues.map(issue -> new IssueResponse(
                issue.getId(),
                issue.getTitle(),
                issue.getStatus(),
                issue.getPriority(),
                issue.getCreatedAt(),
                issue.getReporter().getName(),
                issue.getAssignee() != null? issue.getAssignee().getName() : null));
    }

    @Transactional(readOnly = true)
    public IssueDetailResponse getIssueById(Long id){
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));

        return new IssueDetailResponse(
                issue.getId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getStatus(),
                issue.getPriority(),
                issue.getCreatedAt(),
                issue.getUpdatedAt(),
                issue.getReporter().getName(),
                issue.getReporter().getEmail(),
                issue.getAssignee() != null? issue.getAssignee().getName() : null,
                issue.getAssignee() != null? issue.getAssignee().getEmail() : null
        );
    }

    public IssueDetailResponse updateIssue(Long id, IssueUpdateRequest request){
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));

        if(request.getTitle() != null) issue.setTitle(request.getTitle());
        if(request.getDescription() != null) issue.setDescription(request.getDescription());
        if(request.getPriority() != null) issue.setPriority(request.getPriority());
        if(request.getStatus() != null) issue.setStatus(request.getStatus());

        Issue updated = issueRepository.save(issue);

        return new IssueDetailResponse(
                updated.getId(),
                updated.getTitle(),
                updated.getDescription(),
                updated.getStatus(),
                updated.getPriority(),
                updated.getCreatedAt(),
                updated.getUpdatedAt(),
                updated.getReporter().getName(),
                updated.getReporter().getEmail(),
                updated.getAssignee() != null ? updated.getAssignee().getName() : null,
                updated.getAssignee() != null ? updated.getAssignee().getEmail() : null
        );
    }

    public void deleteById(Long id){
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));
        issueRepository.delete(issue);
    }

    public IssueDetailResponse assignIssue(Long issueId, Long userId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException("Issue Not Found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Illegal Argument Exception"));
        issue.setAssignee(user);

        Issue updated = issueRepository.save(issue);

        return new IssueDetailResponse(
                updated.getId(),
                updated.getTitle(),
                updated.getDescription(),
                updated.getStatus(),
                updated.getPriority(),
                updated.getCreatedAt(),
                updated.getUpdatedAt(),
                updated.getReporter().getName(),
                updated.getReporter().getEmail(),
                updated.getAssignee() != null ? updated.getAssignee().getName() : null,
                updated.getAssignee() != null ? updated.getAssignee().getEmail() : null
        );
    }

    public IssueDetailResponse updateStatus(Long id, IssueStatus newStatus){
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));
        IssueStatus current = issue.getStatus();
        boolean valid =
                (current == IssueStatus.OPEN && newStatus == IssueStatus.IN_PROGRESS) ||
                        (current == IssueStatus.IN_PROGRESS && newStatus == IssueStatus.RESOLVED) ||
                        (current == IssueStatus.RESOLVED && newStatus == IssueStatus.CLOSED);

        if (!valid) {
            throw new IllegalStateException("Invalid status transition from " + current + " to " + newStatus);
        }
        issue.setStatus(newStatus);

        Issue updated = issueRepository.save(issue);

        return new IssueDetailResponse(
                updated.getId(),
                updated.getTitle(),
                updated.getDescription(),
                updated.getStatus(),
                updated.getPriority(),
                updated.getCreatedAt(),
                updated.getUpdatedAt(),
                updated.getReporter().getName(),
                updated.getReporter().getEmail(),
                updated.getAssignee() != null ? updated.getAssignee().getName() : null,
                updated.getAssignee() != null ? updated.getAssignee().getEmail() : null
        );

    }

    @Transactional(readOnly = true)
    public Page<IssueResponse> findIssueByStatus(IssueStatus status, Pageable pageable){
        Page<Issue> issues = issueRepository.findIssueByStatus(status, pageable);

        if(issues.isEmpty()){
            throw new IllegalArgumentException("No issues found");
        }
        return issues.map(issue -> new IssueResponse(
                issue.getId(),
                issue.getTitle(),
                issue.getStatus(),
                issue.getPriority(),
                issue.getCreatedAt(),
                issue.getReporter().getName(),
                issue.getAssignee() != null? issue.getAssignee().getName() : null
        ));
    }

    @Transactional(readOnly = true)
    public Page<IssueResponse> findIssueByPriority(Priority priority, Pageable pageable){
        Page<Issue> issues = issueRepository.findIssueByPriority(priority, pageable);

        if(issues.isEmpty()){
            throw new IllegalArgumentException("No issues found");
        }

        return issues.map(issue -> new IssueResponse(
                issue.getId(),
                issue.getTitle(),
                issue.getStatus(),
                issue.getPriority(),
                issue.getCreatedAt(),
                issue.getReporter().getName(),
                issue.getAssignee() != null? issue.getAssignee().getName() : null
        ));
    }
}
