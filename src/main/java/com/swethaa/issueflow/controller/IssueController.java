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

import com.swethaa.issueflow.dto.IssueResponse;
import com.swethaa.issueflow.dto.IssueDetailResponse;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/issues")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService){
        this.issueService = issueService;
    }

    @PostMapping
    public ResponseEntity<IssueResponse> createIssue(@Valid @RequestBody IssueCreateRequest request, Authentication authentication){
        System.out.println("AUTH NAME = " + authentication.getName());
        String reporterEmail = authentication.getName();
        IssueResponse created = issueService.createIssue(request, reporterEmail);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<Page<IssueResponse>> getAllIssues(Pageable pageable){
        Page<IssueResponse> issues = issueService.getAllIssues(pageable);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDetailResponse> getIssueById(@PathVariable Long id){
        IssueDetailResponse issue = issueService.getIssueById(id);
        return ResponseEntity.ok(issue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueDetailResponse> updateIssue(@PathVariable Long id, @Valid @RequestBody IssueUpdateRequest issueUpdateRequest){
        IssueDetailResponse updated = issueService.updateIssue(id, issueUpdateRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
       issueService.deleteById(id);
       return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('USER','ADMIN')")
    @PutMapping("/{issueId}/assign/{userId}")
    public ResponseEntity<IssueDetailResponse> assignIssue(@PathVariable Long issueId, @PathVariable Long userId){
        IssueDetailResponse updated = issueService.assignIssue(issueId, userId);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("{id}/status")
    public ResponseEntity<IssueDetailResponse> updateStatus(@PathVariable Long id, @RequestBody IssueStatusUpdateRequest request){
        IssueDetailResponse updated = issueService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<IssueResponse>> getIssuesByStatus(@PathVariable("status") IssueStatus issueStatus, Pageable pageable){
        Page<IssueResponse> issues = issueService.findIssueByStatus(issueStatus, pageable);

        return ResponseEntity.ok(issues);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<Page<IssueResponse>> getIssueByPriority(@PathVariable("priority") Priority priority, Pageable pageable){
        Page<IssueResponse> issues = issueService.findIssueByPriority(priority, pageable);

        return ResponseEntity.ok(issues);
    }


}
