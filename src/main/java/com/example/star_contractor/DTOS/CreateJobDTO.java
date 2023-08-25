package com.example.star_contractor.DTOS;

import com.example.star_contractor.Models.User;

import java.time.LocalDateTime;

public class CreateJobDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private String startDate;
    private String threat;
    private Short paymentPercent;
    private String jobStatus;
    private User creatorId;
    private String startLocation;
    private Long distance;
    private boolean bounty_hunting;
    private boolean illegal;
    private boolean mining;
    private boolean combat;
    private boolean salvage;
    private boolean trading;
    private boolean exploring;
    private boolean delivery;
    private boolean pvp;
    private boolean pve;
    private boolean rolePlay;


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

    public User getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(User creatorId) {
        this.creatorId = creatorId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public boolean isBounty_hunting() {
        return bounty_hunting;
    }

    public void setBounty_hunting(boolean bounty_hunting) {
        this.bounty_hunting = bounty_hunting;
    }

    public boolean isIllegal() {
        return illegal;
    }

    public void setIllegal(boolean illegal) {
        this.illegal = illegal;
    }

    public boolean isMining() {
        return mining;
    }

    public void setMining(boolean mining) {
        this.mining = mining;
    }

    public boolean isCombat() {
        return combat;
    }

    public void setCombat(boolean combat) {
        this.combat = combat;
    }

    public boolean isSalvage() {
        return salvage;
    }

    public void setSalvage(boolean salvage) {
        this.salvage = salvage;
    }

    public boolean isTrading() {
        return trading;
    }

    public void setTrading(boolean trading) {
        this.trading = trading;
    }

    public boolean isExploring() {
        return exploring;
    }

    public void setExploring(boolean exploring) {
        this.exploring = exploring;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isPvp() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }

    public boolean isPve() {
        return pve;
    }

    public void setPve(boolean pve) {
        this.pve = pve;
    }

    public boolean isRolePlay() {
        return rolePlay;
    }

    public void setRolePlay(boolean rolePlay) {
        this.rolePlay = rolePlay;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
