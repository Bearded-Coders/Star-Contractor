package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.Categories;
import com.example.star_contractor.Models.HostRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRateRepository extends JpaRepository<HostRating, Integer> {

}
