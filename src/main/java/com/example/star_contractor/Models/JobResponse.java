package com.example.star_contractor.Models;

import jakarta.persistence.*;

@Entity
public class JobResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Jobs job;

    private Boolean response; // true for success, false for failure

    public JobResponse() {
    }
    public JobResponse(Long id, User user, Jobs job, Boolean response) {
        this.id = id;
        this.user = user;
        this.job = job;
        this.response = response;
    }

    public JobResponse(User user, Jobs job, Boolean response) {
        this.user = user;
        this.job = job;
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Jobs getJob() {
        return job;
    }

    public void setJob(Jobs job) {
        this.job = job;
    }

    public Boolean getResponse() {
        return response;
    }

    public void setResponse(Boolean response) {
        this.response = response;
    }
}
