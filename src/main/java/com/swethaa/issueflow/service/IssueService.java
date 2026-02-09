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

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    public IssueService(IssueRepository issueRepository, UserRepository userRepository){
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
    }

    public Issue createIssue(IssueCreateRequest request, String reporterEmail){
        User reporter = userRepository.findByEmail(reporterEmail).orElseThrow(() -> new IllegalArgumentException("Reporter not found"));

        Issue issue = new Issue();
        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        issue.setPriority(request.getPriority());
        issue.setReporter(reporter);

        return issueRepository.save(issue);
    }

    public List<Issue> getAllIssues(){
        List<Issue> issues = issueRepository.findAll();

        if(issues.isEmpty()){
            throw new IllegalArgumentException("No issues found");
        }
        return issues;
    }

    public Issue getIssueById(Long id){
        return issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));
    }

    public Issue updateIssue(Long id, IssueUpdateRequest request){
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));

        if(request.getTitle() != null) issue.setTitle(request.getTitle());
        if(request.getDescription() != null) issue.setDescription(request.getDescription());
        if(request.getPriority() != null) issue.setPriority(request.getPriority());
        if(request.getStatus() != null) issue.setStatus(request.getStatus());

        return issueRepository.save(issue);
    }

    public void deleteById(Long id){
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new IllegalArgumentException ("Issue not found"));
        issueRepository.delete(issue);
    }

    public Issue assignIssue(Long issueId, Long userId) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new IllegalArgumentException("Issue Not Found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Illegal Argument Exception"));
        issue.setAssignee(user);
        return issueRepository.save(issue);
    }

    public Issue updateStatus(Long id, IssueStatus newStatus){
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

        return issueRepository.save(issue);

    }
}
