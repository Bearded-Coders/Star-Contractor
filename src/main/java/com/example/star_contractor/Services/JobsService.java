package com.example.star_contractor.Services;

import com.example.star_contractor.DTOS.JobDetailsDTO;
import com.example.star_contractor.Models.Categories;
import com.example.star_contractor.Models.Comment;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.CategoriesRepository;
import com.example.star_contractor.Repostories.CommentRepository;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsService {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CategoryService categoryService;
    private final UserRepository userDao;
    private final JobRepository jobDao;
    private final CategoriesRepository catDao;
    private final CommentRepository commentDao;

    public  JobsService(UserRepository userDao,
                        JobRepository jobDao,
                        CommentRepository commentDao,
                        CategoriesRepository catDao) {
        this.jobDao = jobDao;
        this.catDao = catDao;
        this.userDao = userDao;
        this.commentDao = commentDao;
    }

    // Find all jobs
    public Page<Jobs> findAllJobs(Pageable pageable) {
        return jobDao.findAll(pageable);
    }

    public Jobs findJobById(Integer id) throws Exception {
        return jobDao.getJobById(id);
    }

    public JobDetailsDTO getJobDetails(Integer jobId, User currentUser) throws Exception {
        JobDetailsDTO jobDetails = new JobDetailsDTO();

        Jobs singleJob = this.findJobById(jobId);
        List<Categories> categories = categoryService.findCategoriesForJob(singleJob);
        List<Comment> comments = commentService.findCommentsForJob(singleJob);

        List<User> applicantsList = singleJob.getApplicantList();
        List<User> acceptedList = singleJob.getAcceptedList();
        List<User> firstFourApplicants = applicantsList.subList(0, Math.min(applicantsList.size(), 4));

        jobDetails.setSingleJob(singleJob);
        jobDetails.setCategories(categories);
        jobDetails.setComments(comments);
        jobDetails.setUser(currentUser);
        jobDetails.setApplicantsList(applicantsList);
        jobDetails.setAcceptedList(acceptedList);
        jobDetails.setFirstFourApplicants(firstFourApplicants);

        return jobDetails;
    }

    // Find jobs by category
    public Page<Jobs> findJobsByCategory(Boolean illegal,
                                         Boolean mining,
                                         Boolean combat,
                                         Boolean salvage,
                                         Boolean trading,
                                         Boolean exploring,
                                         Boolean bountyHunting,
                                         Boolean delivery,
                                         Boolean pvp,
                                         Boolean pve,
                                         Boolean rolePlay,
                                         Pageable pageable) {
        return jobDao.findPaginatedJobsByCategoryTags(illegal, mining, combat, salvage, trading, exploring, bountyHunting, delivery, pvp, pve, rolePlay, pageable);
    }

    public Page<Jobs> findByDescription(String filter, Pageable pageable) {
        return jobDao.findByDescriptionContainingIgnoreCase(filter,pageable);
    }

    // Check if the user has been accepted to the job
    public boolean jobContainsUser(Integer jobId, User user) throws Exception {
        Jobs ratedJob = jobDao.getJobById(jobId);
        User currentUser = userDao.getUserById(user.getId());

        return ratedJob.getAcceptedList().contains(currentUser);
    }
}
