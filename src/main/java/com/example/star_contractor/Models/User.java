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
    private Short avgRating;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creatorId")
    private List<Jobs> myJobs;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "applicants_users",
                joinColumns = {@JoinColumn(name = "applicants_id")},
                inverseJoinColumns = {@JoinColumn(name = "job_id")})
    private List<Jobs> applicantList;

    @OneToMany(mappedBy = "ratedUserId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;

    //delete later

}
