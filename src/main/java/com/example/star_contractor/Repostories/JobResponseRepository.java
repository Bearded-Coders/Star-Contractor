package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.JobResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobResponseRepository extends JpaRepository<JobResponse, Long> {

    List<JobResponse> findAllByJobId(Integer jobId);
}
