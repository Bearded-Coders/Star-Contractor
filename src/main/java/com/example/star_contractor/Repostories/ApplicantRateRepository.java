package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.ApplicantRating;
import com.example.star_contractor.Models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRateRepository extends JpaRepository<ApplicantRating, Integer> {

}
