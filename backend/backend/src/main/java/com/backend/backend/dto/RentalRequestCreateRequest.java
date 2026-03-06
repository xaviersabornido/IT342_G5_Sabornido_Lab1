package com.backend.backend.dto;

import javax.validation.constraints.NotNull;

public class RentalRequestCreateRequest {

    @NotNull(message = "Listing id is required")
    private Long listingId;

    public RentalRequestCreateRequest() {
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }
}

