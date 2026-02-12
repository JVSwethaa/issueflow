package com.swethaa.issueflow.controller;

import com.swethaa.issueflow.entity.Issue;
import com.swethaa.issueflow.service.IssueService;
import org.springframework.web.bind.annotation.*;

import com.swethaa.issueflow.dto.IssueCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import java.util.List;
import com.swethaa.issueflow.dto.IssueUpdateRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import com.swethaa.issueflow.dto.IssueStatusUpdateRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.swethaa.issueflow.entity.IssueStatus;
import com.swethaa.issueflow.entity.Priority;



@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService){
        this.issueService = issueService;
    }

    @PostMapping
    public ResponseEntity<Issue> createIssue(@RequestBody IssueCreateRequest request, Authentication authentication){
        System.out.println("AUTH NAME = " + authentication.getName());
        String reporterEmail = authentication.getName();
        Issue created = issueService.createIssue(request, reporterEmail);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<Page<Issue>> getAllIssues(Pageable pageable){
        Page<Issue> issues = issueService.getAllIssues(pageable);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id){
        Issue issue = issueService.getIssueById(id);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long id, @RequestBody IssueUpdateRequest issueUpdateRequest){
        Issue updated = issueService.updateIssue(id, issueUpdateRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Issue> deleteById(@PathVariable Long id){
       issueService.deleteById(id);
       return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER','ADMIN')")
    @PutMapping("/{issueId}/assign/{userId}")
    public ResponseEntity<Issue> assignIssue(@PathVariable Long issueId, @PathVariable Long userId){
        Issue updated = issueService.assignIssue(issueId, userId);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("{id}/status")
    public ResponseEntity<Issue> updateStatus(@PathVariable Long id, @RequestBody IssueStatusUpdateRequest request){
        Issue updated = issueService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Issue>> getIssuesByStatus(@PathVariable("status") IssueStatus issueStatus, Pageable pageable){
        Page<Issue> issues = issueService.findIssueByStatus(issueStatus, pageable);

        return ResponseEntity.ok(issues);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<Page<Issue>> getIssueByPriority(@PathVariable("priority") Priority priority, Pageable pageable){
        Page<Issue> issues = issueService.findIssueByPriority(priority, pageable);

        return ResponseEntity.ok(issues);
    }


}
