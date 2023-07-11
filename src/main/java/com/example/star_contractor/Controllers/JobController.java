package com.example.star_contractor.Controllers;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Repostories.JobRepository;

import org.springframework.boot.autoconfigure.batch.BatchProperties;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    JobRepository jobsRepository;

    @GetMapping("/jobs")
    public List<Jobs> getAllJobs() {
        return jobsRepository.findAll();
    }

    @GetMapping("/jobs/{id}")
    public Jobs getJob(@PathVariable Integer id) throws Exception {
        Jobs job = jobsRepository.getJobById(id);
        return job;
    }

}
