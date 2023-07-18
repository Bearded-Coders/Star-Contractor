package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Categories;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import com.example.star_contractor.Repostories.CategoriesRepository;
import com.example.star_contractor.Repostories.JobRepository;
import com.example.star_contractor.Repostories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class JobController {

    private final JobRepository jobsRepository;
    private final CategoriesRepository catDao;
    private final UserRepository userDao;

    User user = null;

//    private List<String> applicantsList = new ArrayList<>();
//
//    public List<String> getApplicantsList() {
//        return applicantsList;
//    }

    public JobController(JobRepository jobsRepository, CategoriesRepository catDao, UserRepository userDao) {
        this.jobsRepository = jobsRepository;
        this.catDao = catDao;
        this.userDao = userDao;
    }


    // View all Jobs
    @GetMapping("/jobs")
    public String getAllJobs(Model model, Principal principal) {

        try {
            List<Jobs> jobs = jobsRepository.findAll();

            if (principal != null) {
                user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                model.addAttribute("user", user);
            }

            if (user != null) {
                String userUrl = "/profile/" + user.getId();
                model.addAttribute("userUrl", userUrl);
            }

            model.addAttribute("job", jobs);

            return "index/jobposts";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }

    }

    // Goto specific Job
    @GetMapping("/jobs/{id}")
    public String getJob(@PathVariable Integer id, Model model) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jobs singleJob = jobsRepository.getJobById(id);

        List<Categories> categories = catDao.findCategoriesByJobId(singleJob);

        model.addAttribute("singleJob", singleJob);
        model.addAttribute("category", categories);
        model.addAttribute("user", user);
        return "index/jobdetails";
    }

    // Goto Job Creation Form
    @GetMapping("/jobs/createjob")
    public String jobCreation(Model model, Principal principal) {
        try {
            Jobs job = new Jobs();
            job.setCreatedDate(LocalDateTime.now());
            job.setJobStatus("Active");

            if (principal != null) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                job.setCreatorId(user);
                model.addAttribute("user", user);
            }

            model.addAttribute("job", job);
            return "index/createjob";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Create a Job
    @PostMapping("/jobs/createjob")
    public String addJob(@ModelAttribute Jobs job, Categories categories) {
        try {
            jobsRepository.save(job);
            return "redirect:/jobs";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Update a Job
    @PutMapping("/jobs/{id}")
    public String updateJob(@PathVariable Integer id, @RequestParam Jobs updatedJob) throws Exception {
        try {
            Jobs existingJob = jobsRepository.getJobById(id);
            if (existingJob != null) {
                existingJob.setTitle(updatedJob.getTitle());
                existingJob.setDescription(updatedJob.getDescription());
                existingJob.setThreat(updatedJob.getThreat());
                existingJob.setPaymentPercent(updatedJob.getPaymentPercent());
                existingJob.setJobStatus(updatedJob.getJobStatus());
                existingJob.setStartLocation(updatedJob.getStartLocation());
                existingJob.setDistance(updatedJob.getDistance());
                existingJob.setOutcome(updatedJob.getOutcome());

                jobsRepository.save(existingJob); // Save the updated job
                return "redirect:/jobs"; // Redirect to the jobs page
            } else {
                return "index/errors/jobnotfound"; // Job not found error page
            }
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Apply for job
    @PostMapping("/jobs/apply/{id}")
    public String applyJob(@PathVariable Integer id, @RequestParam(name = "userId") Long usersId) throws Exception {

        Jobs existingJob = jobsRepository.getJobById(id);

        // Fetch the User object corresponding to the usersId
        User applicant = userDao.getUserById(usersId);

        // Add the single applicant to the list
        existingJob.getApplicantList().add(applicant);

        // Save the updated job in the repository (not shown in your code, but you need to do this)
        jobsRepository.save(existingJob);

        return "redirect:/jobs/" + id;
    }





    // Delete a job
    @DeleteMapping("/jobs/{id}")
    public String deleteJob(@PathVariable Integer id) {
        jobsRepository.deleteById(id);
        return "index/profile";
    }

}
