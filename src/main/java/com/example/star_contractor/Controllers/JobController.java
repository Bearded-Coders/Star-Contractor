package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Repostories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JobController {
//    @Autowired
//    JobRepository jobsRepository;

    private final JobRepository jobsRepository;

    public JobController(JobRepository jobsRepository) {
        this.jobsRepository = jobsRepository;
    }

    @GetMapping("/jobs")
    public String getAllJobs(Model model) {
        List<Jobs> jobs = jobsRepository.findAll();

        model.addAttribute("job", jobs);

        return "index/jobposts";

    }

    @GetMapping("/jobs/{id}")
    public String getJob(@PathVariable Integer id, Model model) throws Exception {
        Jobs singleJob = jobsRepository.getJobById(id);

        model.addAttribute("singleJob", singleJob);

        return "index/jobdetails";
    }

    @PostMapping("/jobs")
    public Jobs addJob(@RequestBody Jobs job) {
        jobsRepository.save(job);
        return job;
    }
}
