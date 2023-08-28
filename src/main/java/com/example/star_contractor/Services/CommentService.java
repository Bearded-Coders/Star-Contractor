package com.example.star_contractor.Services;

import com.example.star_contractor.DTOS.CommentDTO;
import com.example.star_contractor.Models.Comment;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.CommentRepository;
import com.example.star_contractor.Repostories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentDao;
    @Autowired
    private JobRepository jobDao;

    public List<Comment> findCommentsForJob(Jobs job) {
        return commentDao.findCommentsByJob(job);
    }

    // Add a comment
    public void addComment(CommentDTO commentDTO) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jobs singleJob = jobDao.getJobById(commentDTO.getJobId());

        Comment comment = new Comment();
        comment.setJob(singleJob);
        comment.setUser(user);
        comment.setContent(commentDTO.getContent());

        commentDao.save(comment);
    }

    // Delete a comment
    public void deleteComment(Long commentId) throws Exception {
        Comment comment = commentDao.findById(commentId);
        if (comment != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (comment.getUser().getId().equals(user.getId())) {
                commentDao.delete(comment);
            } else {
                throw new Exception("You don't have permission to delete this comment");
            }
        }
    }
}
