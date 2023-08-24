package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import com.example.star_contractor.Services.JobsService;
import com.example.star_contractor.Services.RatingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RateController {
    private final RatingService ratingService;
    private final JobsService jobsService;
    private final UserRepository userDao;
    private final JobRepository jobDao;


    public RateController(UserRepository userDao,
                          JobRepository jobDao,
                          RatingService ratingService,
                          JobsService jobsService) {
        this.userDao = userDao;
        this.jobDao = jobDao;
        this.ratingService = ratingService;
        this.jobsService = jobsService;
    }

    @GetMapping("/rate-host-form")
    public String showRateHostForm(
            @RequestParam Long userId,
            @RequestParam Integer jobId,
            Model model) throws Exception {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            boolean hasRated = ratingService.hasRated(jobId, user);

            boolean jobContainsUser = jobsService.jobContainsUser(jobId, user);

            model.addAttribute("userId", userId);
            model.addAttribute("jobId", jobId);
            model.addAttribute("hasRated", hasRated);
            model.addAttribute("jobContainsUser", jobContainsUser);

            return "index/hostrating";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @PostMapping("/rate-host")
    public String rateHost(
            @RequestParam Long userId,
            @RequestParam Integer jobId,
            @RequestParam Short ratingValue) throws Exception {

        try {
            ratingService.createHostRating(userId, jobId, ratingValue);

            System.out.println("Creator was rated");

            return "redirect:/jobs/" + jobId;
        } catch (Exception e) {
            return e.toString();
        }
    }
}
