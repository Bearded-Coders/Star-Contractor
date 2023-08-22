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

    public HostRating() {
    }

    public HostRating(Integer id, Short rating, User ratedHost) {
        this.id = id;
        this.rating = rating;
        this.ratedHost = ratedHost;
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
}