package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.User;
import com.example.star_contractor.Models.Comment;
import com.example.star_contractor.Models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByJob(Jobs job);

}
