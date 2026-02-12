package com.swethaa.issueflow.repository;

import com.swethaa.issueflow.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByIssue_Id(Long issueId);
}
