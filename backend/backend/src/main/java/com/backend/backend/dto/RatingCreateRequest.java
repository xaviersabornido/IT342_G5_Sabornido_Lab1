package com.backend.backend.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RatingCreateRequest {

    @NotNull(message = "Rental request id is required")
    private Long rentalRequestId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private int ratingValue;

    private String comment;

    public RatingCreateRequest() {
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
}

