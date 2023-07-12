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




    @OneToMany(mappedBy = "ratedUserId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rating> ratings;

//    Constructors


    public User() {
    }

    public User(Long id, String username, String password, String email, String startingArea, String profilePic, Short avgRating, List<Jobs> myJobs, List<Rating> ratings) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.startingArea = startingArea;
        this.profilePic = profilePic;
        this.avgRating = avgRating;
        this.myJobs = myJobs;
        this.ratings = ratings;
    }

    public User(String username, String password, String email, String startingArea, String profilePic, Short avgRating, List<Jobs> myJobs, List<Rating> ratings) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.startingArea = startingArea;
        this.profilePic = profilePic;
        this.avgRating = avgRating;
        this.myJobs = myJobs;
        this.ratings = ratings;
    }

//    getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStartingArea() {
        return startingArea;
    }

    public void setStartingArea(String startingArea) {
        this.startingArea = startingArea;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Short getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Short avgRating) {
        this.avgRating = avgRating;
    }

    public List<Jobs> getMyJobs() {
        return myJobs;
    }

    public void setMyJobs(List<Jobs> myJobs) {
        this.myJobs = myJobs;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
