package com.example.star_contractor.Services;

import com.example.star_contractor.Models.Categories;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Repostories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoriesRepository catDao;

    public List<Categories> findCategoriesForJob(Jobs job) {
        return catDao.findCategoriesByJobId(job);
    }

    // Other category-related methods
}
