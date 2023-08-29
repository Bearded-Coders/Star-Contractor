package com.example.star_contractor.Services;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userDao;
    private final JobRepository jobDao;

    public UserService(UserRepository userDao, JobRepository jobDao)
    {
        this.userDao = userDao;
        this.jobDao = jobDao;
    }

    // Get user By Id
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    public Page<User> getJobApplicants(Integer id, Pageable pageable) throws Exception {
        return userDao.findApplicantsByJobId(id,pageable);
    }

    public List<Jobs> getUsersActiveJobs(User user) {
        List<Jobs> usersActiveJobs = new ArrayList<>();
        List<Jobs> usersJobs = jobDao.findJobsByCreatorId(user);

        for (Jobs job : usersJobs) {
            if ("Active".equals(job.getJobStatus())) { // Check if job status is "Active"
                usersActiveJobs.add(job); // Add the job to the active jobs list
            }
        }

        return usersActiveJobs;
    }
}
