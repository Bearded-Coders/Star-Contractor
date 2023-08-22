package com.example.star_contractor.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
@Entity
@JsonIgnoreProperties
public class ApplicantRating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Short rating;

    @ManyToOne
    private User ratedApplicant;

    public ApplicantRating() {
    }

    public ApplicantRating(Integer id, Short rating, User ratedApplicant) {
        this.id = id;
        this.rating = rating;
        this.ratedApplicant = ratedApplicant;
    }

    public ApplicantRating(Short rating, User ratedApplicant) {
        this.rating = rating;
        this.ratedApplicant = ratedApplicant;
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
    public User getRatedApplicant() {
        return ratedApplicant;
    }

    public void setRatedApplicant(User ratedApplicant) {
        this.ratedApplicant = ratedApplicant;
    }
}