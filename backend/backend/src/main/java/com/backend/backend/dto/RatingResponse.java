package com.backend.backend.dto;

import java.time.LocalDateTime;

public class RatingResponse {

    private Long id;
    private Long ownerId;
    private String ownerUsername;
    private Long renterId;
    private String renterUsername;
    private Long rentalRequestId;
    private int ratingValue;
    private String comment;
    private LocalDateTime createdAt;

    public RatingResponse() {
    }

    public RatingResponse(Long id, Long ownerId, String ownerUsername, Long renterId, String renterUsername,
                          Long rentalRequestId, int ratingValue, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerUsername = ownerUsername;
        this.renterId = renterId;
        this.renterUsername = renterUsername;
        this.rentalRequestId = rentalRequestId;
        this.ratingValue = ratingValue;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Long getRenterId() {
        return renterId;
    }

    public void setRenterId(Long renterId) {
        this.renterId = renterId;
    }

    public String getRenterUsername() {
        return renterUsername;
    }

    public void setRenterUsername(String renterUsername) {
        this.renterUsername = renterUsername;
    }

    public Long getRentalRequestId() {
        return rentalRequestId;
    }

    public void setRentalRequestId(Long rentalRequestId) {
        this.rentalRequestId = rentalRequestId;
    }

    public int getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(int ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

