package com.example.star_contractor.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@JsonIgnoreProperties
public class Jobs implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String creatorEmail;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private String threat;

    @Column(nullable = false)
    private Short paymentPercent;

    @Column(nullable = false)
    private String jobStatus;

    @Column(nullable = false)
    private String startLocation;

    @Column(nullable = false)
    private Long distance;

    @ManyToOne
    @JoinColumn(name = "creatorId")
    private User creatorId;
    @Column
    private Boolean outcome;

    @ManyToMany(mappedBy = "appliedJobs")
    private List<User> applicantList = new ArrayList<>();

    @ManyToMany(mappedBy = "acceptedJobs")
    private List<User> acceptedList = new ArrayList<>();

    @OneToMany(mappedBy = "jobId", cascade = CascadeType.ALL)
    private List<Categories> categories = new ArrayList<>();

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "jobId")
    private List<HostRating> ratedHosts;

    @OneToMany(mappedBy = "jobId")
    private List<HostRating> ratingUsers;

    public Jobs() {
    }

    public Jobs(Integer id, String creatorEmail, String title, String description, String startDate, LocalDateTime createdDate, String threat, Short paymentPercent, String jobStatus, String startLocation, Long distance, User creatorId, Boolean outcome, List<User> applicantList, List<User> acceptedList, List<Categories> categories, List<Comment> comments, List<HostRating> ratedHosts, List<HostRating> ratingUsers) {
        this.id = id;
        this.creatorEmail = creatorEmail;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.createdDate = createdDate;
        this.threat = threat;
        this.paymentPercent = paymentPercent;
        this.jobStatus = jobStatus;
        this.startLocation = startLocation;
        this.distance = distance;
        this.creatorId = creatorId;
        this.outcome = outcome;
        this.applicantList = applicantList;
        this.acceptedList = acceptedList;
        this.categories = categories;
        this.comments = comments;
        this.ratedHosts = ratedHosts;
        this.ratingUsers = ratingUsers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getThreat() {
        return threat;
    }

    public void setThreat(String threat) {
        this.threat = threat;
    }

    public Short getPaymentPercent() {
        return paymentPercent;
    }

    public void setPaymentPercent(Short paymentPercent) {
        this.paymentPercent = paymentPercent;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public User getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(User creatorId) {
        this.creatorId = creatorId;
    }

    public Boolean getOutcome() {
        return outcome;
    }

    public void setOutcome(Boolean outcome) {
        this.outcome = outcome;
    }

    public List<User> getApplicantList() {
        return applicantList;
    }

    public void setApplicantList(List<User> applicantList) {
        this.applicantList = applicantList;
    }

    public List<User> getAcceptedList(){return acceptedList;}

    public void setAcceptedList(List<User> acceptedList){this.acceptedList = acceptedList;}

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

    public void addApplicant(User applicant) {
        applicantList.add(applicant);
    }

    public void removeApplicant(User applicant) {
        applicantList.remove(applicant);
    }

    public void addAcceptedUser(User user) {
        acceptedList.add(user);
    }

    public void removeAcceptedUser(User user) {
        acceptedList.remove(user);
    }

    public List<HostRating> getRatedHosts() {
        return ratedHosts;
    }

    public void setRatedHosts(List<HostRating> ratedHosts) {
        this.ratedHosts = ratedHosts;
    }

    public List<HostRating> getRatingUsers() {
        return ratingUsers;
    }

    public void setRatingUsers(List<HostRating> ratingUsers) {
        this.ratingUsers = ratingUsers;
    }

    //    @Override
//    public String toString() {
//        return "Jobs{" +
//                "id=" + id +
//                ", title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", startDate=" + startDate +
//                ", createdDate=" + createdDate +
//                ", threat='" + threat + '\'' +
//                ", paymentPercent=" + paymentPercent +
//                ", jobStatus='" + jobStatus + '\'' +
//                ", startLocation='" + startLocation + '\'' +
//                ", distance=" + distance +
//                ", creatorId=" + creatorId +
//                ", outcome=" + outcome +
//                ", applicantList=" + applicantList +
//                ", categories=" + categories +
//                '}';
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Jobs jobs = (Jobs) o;
//        return Objects.equals(id, jobs.id) && Objects.equals(title, jobs.title) && Objects.equals(description, jobs.description) && Objects.equals(startDate, jobs.startDate) && Objects.equals(createdDate, jobs.createdDate) && Objects.equals(threat, jobs.threat) && Objects.equals(paymentPercent, jobs.paymentPercent) && Objects.equals(jobStatus, jobs.jobStatus) && Objects.equals(startLocation, jobs.startLocation) && Objects.equals(distance, jobs.distance) && Objects.equals(creatorId, jobs.creatorId) && Objects.equals(outcome, jobs.outcome) && Objects.equals(applicantList, jobs.applicantList) && Objects.equals(categories, jobs.categories);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, title, description, startDate, createdDate, threat, paymentPercent, jobStatus, startLocation, distance, creatorId, outcome, applicantList, categories);
//    }
}
