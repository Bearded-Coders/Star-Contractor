package com.example.star_contractor.Controllers;

import com.example.star_contractor.Repostories.FriendRepository;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.stereotype.Controller;

@Controller
public class RateController {

    private final UserRepository userDao;
    private final JobRepository jobDao;
    private final FriendRepository friendDao;

    public RateController(UserRepository userDao, JobRepository jobDao, FriendRepository friendDao) {
        this.userDao = userDao;
        this.jobDao = jobDao;
        this.friendDao = friendDao;
    }




}
