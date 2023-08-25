package com.example.star_contractor.DTOS;

import com.example.star_contractor.Models.Categories;
import com.example.star_contractor.Models.Comment;
import com.example.star_contractor.Models.Jobs;
import com.example.star_contractor.Models.User;

import java.util.List;

public class JobDetailsDTO {
    private Jobs singleJob;
    private List<Categories> categories;
    private List<Comment> comments;
    private User user;
    private List<User> applicantsList;
    private List<User> acceptedList;
    private List<User> firstFourApplicants;

    // Getters and setters
    public Jobs getSingleJob() {
        return singleJob;
    }

    public void setSingleJob(Jobs singleJob) {
        this.singleJob = singleJob;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getApplicantsList() {
        return applicantsList;
    }

    public void setApplicantsList(List<User> applicantsList) {
        this.applicantsList = applicantsList;
    }

    public List<User> getAcceptedList() {
        return acceptedList;
    }

    public void setAcceptedList(List<User> acceptedList) {
        this.acceptedList = acceptedList;
    }

    public List<User> getFirstFourApplicants() {
        return firstFourApplicants;
    }

    public void setFirstFourApplicants(List<User> firstFourApplicants) {
        this.firstFourApplicants = firstFourApplicants;
    }
}