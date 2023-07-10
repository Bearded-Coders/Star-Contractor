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

    @OneToMany
    @JoinColumn(name = "creator_id")
    private User creator_Id;

    @OneToMany
    @JoinColumn(name = "applicants_id")
    private User applicant_Id;

    123
}
