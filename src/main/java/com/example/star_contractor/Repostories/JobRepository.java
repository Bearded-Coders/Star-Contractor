package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Jobs, Integer> {
    Jobs getJobById(Integer id) throws Exception;
    List<Jobs> findJobsByCreatorId(User user);
    List<Jobs> findJobsByApplicantListContains(User user);

}
