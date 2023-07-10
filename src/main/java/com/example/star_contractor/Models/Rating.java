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

   //delete this later
}
