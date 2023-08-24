package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.HostRating;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import com.example.star_contractor.Services.RatingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RateController {
    private final RatingService ratingService;

    private final UserRepository userDao;
    private final JobRepository jobDao;


    public RateController(UserRepository userDao, JobRepository jobDao, RatingService ratingService) {
        this.userDao = userDao;
        this.jobDao = jobDao;
        this.ratingService = ratingService;
    }

    @GetMapping("/rate-host-form")
    public String showRateHostForm(
            @RequestParam Long userId,
            @RequestParam Integer jobId,
            Model model) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean hasRated = ratingService.hasRated(jobId, user);

        boolean jobContainsUser = ratingService.jobContainsUser(jobId, user);

        if (hasRated) {
            System.out.println("*************** user has already rated *************");
        } else {
            System.out.println("************* User has not rated creator yet ****************");
        }

        if (jobContainsUser) {
            System.out.println("************** User is apart of the job ******************");
        } else {
            System.out.println("************** User is not apart of this job ******************");
        }



        if (jobContainsUser) {
            System.out.println("User is apart of the job");
        } else {
            System.out.println("User is not apart of this job");
        }

        model.addAttribute("userId", userId);
        model.addAttribute("jobId", jobId);
        model.addAttribute("hasRated", hasRated);
        model.addAttribute("jobContainsUser", jobContainsUser);

        return "index/hostrating";
    }

    @PostMapping("/rate-host")
    public String rateHost(
            @RequestParam Long userId,
            @RequestParam Integer jobId,
            @RequestParam Short ratingValue) throws Exception {

        try {
            // Retrieve user, job, and validate inputs
            User ratingUser = userDao.getUserById(userId);
            Jobs ratedJob = jobDao.getJobById(jobId);
            User ratedUser = jobDao.getJobById(jobId).getCreatorId();
            User rated = userDao.getUserById(ratedUser.getId());

            // Print the list of users who have rated the host of this job
            List<String> ratedUsers = new ArrayList<>();
            for (HostRating rating : ratedJob.getRatingUsers()) {
                ratedUsers.add(rating.getRatingUser().getUsername());
            }
            System.out.println("Users who have rated the host of this job: " + ratedUsers);

            if (ratingUser == null) {
                return "index/errors/exception";
            }


            // Create and save the host rating
            HostRating hostRating = new HostRating();
            hostRating.setRating(ratingValue);
            hostRating.setRatingUser(ratingUser);
            hostRating.setRatedHost(ratedJob.getCreatorId());
            hostRating.setJobId(ratedJob);

            ratingService.createHostRating(hostRating); // Update RatingService to handle rating creation

            System.out.println("Creator was rated");
            return "redirect:/jobs/" + jobId;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
