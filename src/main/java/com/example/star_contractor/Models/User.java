package com.example.star_contractor.Models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "friendship",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")}
    )
    private List<User> friendsList;
//    Constructors

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    public User() {
    }

    public User(Long id, String username, String password, String email, String startingArea, String profilePic, Short avgRating, List<Jobs> myJobs, List<Rating> ratings, List<User> friendsList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.startingArea = startingArea;
        this.profilePic = profilePic;
        this.avgRating = avgRating;
        this.myJobs = myJobs;
        this.ratings = ratings;
        this.friendsList = friendsList;
    }

    public User(String username, String password, String email, String startingArea, String profilePic, Short avgRating, List<Jobs> myJobs, List<Rating> ratings, List<User> friendsList) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.startingArea = startingArea;
        this.profilePic = profilePic;
        this.avgRating = avgRating;
        this.myJobs = myJobs;
        this.ratings = ratings;
        this.friendsList = friendsList;
    }

    //For security  add email username with unique constraints
    //The constructor User(User copy) defined in this class is a common pattern in Java called a copy constructor. It is used as an alternative to cloning an object. Instead of using the method clone, we create a new object using the current values of another. This will be used in order to fulfill the contract defined by the interfaces in the security package.
    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        startingArea = copy.startingArea;
        profilePic = copy.profilePic;
        avgRating = copy.avgRating;
        myJobs = copy.myJobs;
        ratings = copy.ratings;
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

    public List<User> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<User> friendsList) {
        this.friendsList = friendsList;
    }
}
