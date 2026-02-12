package com.swethaa.issueflow.repository;

import com.swethaa.issueflow.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.swethaa.issueflow.entity.Priority;
import com.swethaa.issueflow.entity.IssueStatus;


public interface IssueRepository extends JpaRepository<Issue, Long>{
    Page<Issue> findIssueByPriority(Priority priority, Pageable pageable);

    Page<Issue> findIssueByStatus(IssueStatus status, Pageable Pageable);
}
