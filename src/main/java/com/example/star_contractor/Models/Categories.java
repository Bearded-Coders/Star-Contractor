package com.example.star_contractor.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
@Entity
@JsonIgnoreProperties
public class Categories implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Boolean illegal;

    @Column(nullable = false)
    private Boolean mining;

    @Column(nullable = false)
    private Boolean combat;

    @Column(nullable = false)
    private Boolean salvage;

    @Column(nullable = false)
    private Boolean trading;

    @Column(nullable = false)
    private Boolean exploring;

    @Column(nullable = false)
    private Boolean bounty_hunting;

    @Column(nullable = false)
    private Boolean delivery;

    @Column(nullable = false)
    private Boolean pvp;

    @Column(nullable = false)
    private Boolean pve;

    @Column(nullable = false)
    private Boolean rolePlay;

    @ManyToOne
    @JoinColumn(name = "jobId")
    private jobs jobId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIllegal() {
        return illegal;
    }

    public void setIllegal(Boolean illegal) {
        this.illegal = illegal;
    }

    public Boolean getMining() {
        return mining;
    }

    public void setMining(Boolean mining) {
        this.mining = mining;
    }

    public Boolean getCombat() {
        return combat;
    }

    public void setCombat(Boolean combat) {
        this.combat = combat;
    }

    public Boolean getSalvage() {
        return salvage;
    }

    public void setSalvage(Boolean salvage) {
        this.salvage = salvage;
    }

    public Boolean getTrading() {
        return trading;
    }

    public void setTrading(Boolean trading) {
        this.trading = trading;
    }

    public Boolean getExploring() {
        return exploring;
    }

    public void setExploring(Boolean exploring) {
        this.exploring = exploring;
    }

    public Boolean getBounty_hunting() {
        return bounty_hunting;
    }

    public void setBounty_hunting(Boolean bounty_hunting) {
        this.bounty_hunting = bounty_hunting;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Boolean getPvp() {
        return pvp;
    }

    public void setPvp(Boolean pvp) {
        this.pvp = pvp;
    }

    public Boolean getPve() {
        return pve;
    }

    public void setPve(Boolean pve) {
        this.pve = pve;
    }

    public Boolean getRolePlay() {
        return rolePlay;
    }

    public void setRolePlay(Boolean rolePlay) {
        this.rolePlay = rolePlay;
    }

    public jobs getJobId() {
        return jobId;
    }

    public void setJobId(jobs jobId) {
        this.jobId = jobId;
    }

    public Categories(Integer id, Boolean illegal, Boolean mining, Boolean combat, Boolean salvage, Boolean trading, Boolean exploring, Boolean bounty_hunting, Boolean delivery, Boolean pvp, Boolean pve, Boolean rolePlay, jobs jobId) {
        this.id = id;
        this.illegal = illegal;
        this.mining = mining;
        this.combat = combat;
        this.salvage = salvage;
        this.trading = trading;
        this.exploring = exploring;
        this.bounty_hunting = bounty_hunting;
        this.delivery = delivery;
        this.pvp = pvp;
        this.pve = pve;
        this.rolePlay = rolePlay;
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + id +
                ", illegal=" + illegal +
                ", mining=" + mining +
                ", combat=" + combat +
                ", salvage=" + salvage +
                ", trading=" + trading +
                ", exploring=" + exploring +
                ", bounty_hunting=" + bounty_hunting +
                ", delivery=" + delivery +
                ", pvp=" + pvp +
                ", pve=" + pve +
                ", rolePlay=" + rolePlay +
                ", jobId=" + jobId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return Objects.equals(id, that.id) && Objects.equals(illegal, that.illegal) && Objects.equals(mining, that.mining) && Objects.equals(combat, that.combat) && Objects.equals(salvage, that.salvage) && Objects.equals(trading, that.trading) && Objects.equals(exploring, that.exploring) && Objects.equals(bounty_hunting, that.bounty_hunting) && Objects.equals(delivery, that.delivery) && Objects.equals(pvp, that.pvp) && Objects.equals(pve, that.pve) && Objects.equals(rolePlay, that.rolePlay) && Objects.equals(jobId, that.jobId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, illegal, mining, combat, salvage, trading, exploring, bounty_hunting, delivery, pvp, pve, rolePlay, jobId);
    }
}
