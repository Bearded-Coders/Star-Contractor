package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Repostories.JobRepository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class JobController {

    private final JobRepository jobsRepository;

    public JobController(JobRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    // View all Jobs
    @GetMapping("/jobs")
    public String getAllJobs(Model model) {
        List<Jobs> jobs = jobsRepository.findAll();

        model.addAttribute("job", jobs);

        return "index/jobposts";

    }

    // Goto specific Job
    @GetMapping("/jobs/{id}")
    public String getJob(@PathVariable Integer id, Model model) throws Exception {
        Jobs singleJob = jobsRepository.getJobById(id);

        model.addAttribute("singleJob", singleJob);

        return "index/jobdetails";
    }

    // Goto Job Creation Form
    @GetMapping("/jobs/createjob")
    public String jobCreation() {
        try {
            return "index/createjob";
        } catch (Exception e) {
            return "index/errors/exception"; // Exception occurred error page
        }
    }

    // Create a Job
    @PostMapping("/jobs/createjob")
    public String addJob(@RequestParam Jobs job) {
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

    // Delete a job
    @DeleteMapping("/jobs/{id}")
    public String deleteJob(@PathVariable Integer id) {
        jobsRepository.deleteById(id);
        return "index/profile";
    }

}
