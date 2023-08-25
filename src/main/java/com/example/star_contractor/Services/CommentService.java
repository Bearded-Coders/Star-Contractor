package com.example.star_contractor.Services;

import com.example.star_contractor.Models.Comment;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Repostories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findCommentsForJob(Jobs job) {
        return commentRepository.findCommentsByJob(job);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    // Other methods related to comment management
}
