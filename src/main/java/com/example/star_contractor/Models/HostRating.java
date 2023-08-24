package com.example.star_contractor.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@JsonIgnoreProperties
public class HostRating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private Short rating;
    @ManyToOne
    private User ratedHost;

    @ManyToOne
    private User ratingUser;

    @ManyToOne
    @JoinColumn(name = "jobId", referencedColumnName = "id")
    private Jobs jobId;

    public HostRating() {
    }

    public HostRating(Integer id, Short rating, User ratedHost, User ratingUser, Jobs jobId) {
        this.id = id;
        this.rating = rating;
        this.ratedHost = ratedHost;
        this.ratingUser = ratingUser;
        this.jobId = jobId;
    }

    public HostRating(Short rating, User ratedHost) {
        this.rating = rating;
        this.ratedHost = ratedHost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getRating() {
        return rating;
    }
    public void setRating(Short rating) {
        this.rating = rating;
    }
    public User getRatedHost() {
        return ratedHost;
    }

    public void setRatedHost(User ratedHost) {
        this.ratedHost = ratedHost;
    }

    public User getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(User ratingUser) {
        this.ratingUser = ratingUser;
    }

    public Jobs getJobId() {
        return jobId;
    }

    public void setJobId(Jobs jobId) {
        this.jobId = jobId;
    }
}