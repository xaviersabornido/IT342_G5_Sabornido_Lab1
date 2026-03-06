package com.backend.backend.dto;

import java.time.LocalDateTime;

public class RentalRequestResponse {

    private Long id;
    private Long listingId;
    private String listingTitle;
    private Long renterId;
    private String renterUsername;
    private String status;
    private LocalDateTime requestedAt;
    private LocalDateTime updatedAt;

    public RentalRequestResponse() {
    }

    public RentalRequestResponse(Long id, Long listingId, String listingTitle, Long renterId, String renterUsername,
                                 String status, LocalDateTime requestedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.listingId = listingId;
        this.listingTitle = listingTitle;
        this.renterId = renterId;
        this.renterUsername = renterUsername;
        this.status = status;
        this.requestedAt = requestedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

