package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();

        User userProfile = userDao.findById(id).orElse(null);
        List<Jobs> userJobs = jobDao.findJobsByCreatorId(userDao.getReferenceById(id));
        List<Jobs> appliedJobs = jobDao.findJobsByApplicantListContains(userDao.getReferenceById(id));

        model.addAttribute("userProfileLink", userProfile);
        model.addAttribute("myJobs", userJobs);
        model.addAttribute("grabId", userId);
        model.addAttribute("user", user);
        model.addAttribute("appliedJobs", appliedJobs);


        return "index/profile";
    }
}
