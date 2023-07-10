package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Jobs, Integer> {
    Jobs getJobById(Integer id) throws Exception;
}
