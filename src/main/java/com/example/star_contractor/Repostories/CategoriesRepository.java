package com.example.star_contractor.Repostories;

import com.example.star_contractor.Models.Categories;
import com.example.star_contractor.Models.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findCategoriesByJobId(Jobs id);
}
