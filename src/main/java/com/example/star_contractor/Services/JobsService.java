package com.example.star_contractor.Services;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class JobsService {
    private final UserRepository userDao;
    private final JobRepository jobDao;

    public  JobsService(UserRepository userDao, JobRepository jobDao) {
        this.jobDao = jobDao;
        this.userDao = userDao;
    }


    // Check if the user has been accepted to the job
    public boolean jobContainsUser(Integer jobId, User user) throws Exception {
        Jobs ratedJob = jobDao.getJobById(jobId);
        User currentUser = userDao.getUserById(user.getId());

        return ratedJob.getAcceptedList().contains(currentUser);
    }
}
