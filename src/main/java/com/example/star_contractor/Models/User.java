package com.example.star_contractor.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String startingArea;
    @Column()
    private String profilePic;
    @Column()
    private int avgRating;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator_id")
    private List<jobs> myJobs;

}
