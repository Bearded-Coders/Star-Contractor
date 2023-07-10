package com.example.star_contractor.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
@Entity
@JsonIgnoreProperties
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Short rating;

    @ManyToOne
    private User ratedUserId;

    public Rating() {
    }
    public Rating(Integer id, Short rating, User ratedUserId) {
        this.id = id;
        this.rating = rating;
        this.ratedUserId = ratedUserId;
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

    public User getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(User ratedUserId) {
        this.ratedUserId = ratedUserId;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", rating=" + rating +
                ", ratedUserId=" + ratedUserId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return Objects.equals(id, rating1.id) && Objects.equals(rating, rating1.rating) && Objects.equals(ratedUserId, rating1.ratedUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rating, ratedUserId);
    }
}
