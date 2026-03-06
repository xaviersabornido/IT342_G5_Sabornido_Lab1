package com.backend.backend.controller;

import com.backend.backend.dto.RatingCreateRequest;
import com.backend.backend.dto.RatingResponse;
import com.backend.backend.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody RatingCreateRequest request
    ) {
        RatingResponse response = ratingService.createRating(userDetails.getUsername(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<RatingResponse>> getOwnerRatings(@PathVariable Long ownerId) {
        return ResponseEntity.ok(ratingService.getRatingsForOwner(ownerId));
    }

    @GetMapping("/owner/{ownerId}/summary")
    public ResponseEntity<Map<String, Object>> getOwnerRatingSummary(@PathVariable Long ownerId) {
        return ResponseEntity.ok(ratingService.getOwnerRatingSummary(ownerId));
    }
}

