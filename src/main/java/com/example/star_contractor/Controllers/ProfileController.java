package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final JobRepository jobDao;

    public ProfileController(UserRepository userDao, JobRepository jobDao) {
        this.userDao = userDao;
        this.jobDao = jobDao;
    }


    @GetMapping("/profile/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        User userProfile = userDao.findById(id).orElse(null);
        List<Jobs> userJobs = jobDao.findJobsByCreatorId(userDao.getReferenceById(id));
//        Jobs myJobs = jobDao.get
        model.addAttribute("userProfileLink", userProfile);
        model.addAttribute("myJobs", userJobs);



        return "index/profile";
    }
}
