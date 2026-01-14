package com.swethaa.issueflow.repository;

import com.swethaa.issueflow.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long>{
}
