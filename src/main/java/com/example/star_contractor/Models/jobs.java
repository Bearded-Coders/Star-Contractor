package com.example.star_contractor.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
@Entity
@JsonIgnoreProperties
@Table(name = "jobs")
public class jobs implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate = new Date();

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date createdDate;

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

//    @ManyToMany
    @JoinColumn(name = "applicantsId")
    private User applicantId;

    @Column(nullable = false)
    private Boolean outcome;

    public jobs(Integer id, String title, String description, Date startDate, Date createdDate, String threat, Short paymentPercent, String jobStatus, String startLocation, Long distance, User creatorId, User applicantId, Boolean outcome) {
        this.id = id;
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
        this.applicantId = applicantId;
        this.outcome = outcome;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
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

    public User getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(User applicantId) {
        this.applicantId = applicantId;
    }

    public Boolean getOutcome() {
        return outcome;
    }

    public void setOutcome(Boolean outcome) {
        this.outcome = outcome;
    }



    @Override
    public String toString() {
        return "jobs{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", createdDate=" + createdDate +
                ", threat='" + threat + '\'' +
                ", paymentPercent=" + paymentPercent +
                ", jobStatus='" + jobStatus + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", distance=" + distance +
                ", creatorId=" + creatorId +
                ", applicantId=" + applicantId +
                ", outcome=" + outcome +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        jobs jobs = (jobs) o;
        return Objects.equals(id, jobs.id) && Objects.equals(title, jobs.title) && Objects.equals(description, jobs.description) && Objects.equals(startDate, jobs.startDate) && Objects.equals(createdDate, jobs.createdDate) && Objects.equals(threat, jobs.threat) && Objects.equals(paymentPercent, jobs.paymentPercent) && Objects.equals(jobStatus, jobs.jobStatus) && Objects.equals(startLocation, jobs.startLocation) && Objects.equals(distance, jobs.distance) && Objects.equals(creatorId, jobs.creatorId) && Objects.equals(applicantId, jobs.applicantId) && Objects.equals(outcome, jobs.outcome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startDate, createdDate, threat, paymentPercent, jobStatus, startLocation, distance, creatorId, applicantId, outcome);
    }
}
