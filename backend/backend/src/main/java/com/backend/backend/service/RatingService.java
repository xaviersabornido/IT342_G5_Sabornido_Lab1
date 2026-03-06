package com.backend.backend.service;

import com.backend.backend.dto.RatingCreateRequest;
import com.backend.backend.dto.RatingResponse;
import com.backend.backend.entity.Listing;
import com.backend.backend.entity.Rating;
import com.backend.backend.entity.RentalRequest;
import com.backend.backend.entity.User;
import com.backend.backend.exception.InvalidCredentialsException;
import com.backend.backend.repository.RatingRepository;
import com.backend.backend.repository.RentalRequestRepository;
import com.backend.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final RentalRequestRepository rentalRequestRepository;
    private final UserRepository userRepository;

    public RatingService(RatingRepository ratingRepository,
                         RentalRequestRepository rentalRequestRepository,
                         UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.rentalRequestRepository = rentalRequestRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RatingResponse createRating(String renterUsername, RatingCreateRequest request) {
        RentalRequest rentalRequest = rentalRequestRepository.findById(request.getRentalRequestId())
                .orElseThrow(() -> new InvalidCredentialsException("Rental request not found"));

        if (!"APPROVED".equalsIgnoreCase(rentalRequest.getStatus())) {
            throw new InvalidCredentialsException("You can only rate after approval");
        }

        if (!rentalRequest.getRenter().getUsername().equals(renterUsername)) {
            throw new InvalidCredentialsException("You are not allowed to rate this rental");
        }

        ratingRepository.findByRentalRequest(rentalRequest).ifPresent(r -> {
            throw new InvalidCredentialsException("Rating already submitted for this rental");
        });

        User owner = rentalRequest.getListing().getOwner();
        User renter = rentalRequest.getRenter();

        Rating rating = new Rating();
        rating.setOwner(owner);
        rating.setRenter(renter);
        rating.setRentalRequest(rentalRequest);
        rating.setRatingValue(request.getRatingValue());
        rating.setComment(request.getComment());

        Rating saved = ratingRepository.save(rating);
        return toResponse(saved);
    }

    public List<RatingResponse> getRatingsForOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new InvalidCredentialsException("Owner not found"));

        return ratingRepository.findByOwner(owner).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Map<String, Object> getOwnerRatingSummary(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new InvalidCredentialsException("Owner not found"));

        List<Rating> ratings = ratingRepository.findByOwner(owner);

        DoubleSummaryStatistics stats = ratings.stream()
                .collect(Collectors.summarizingDouble(Rating::getRatingValue));

        double average = ratings.isEmpty() ? 0.0 : stats.getAverage();

        return Map.of(
                "ownerId", ownerId,
                "count", ratings.size(),
                "average", average
        );
    }

    private RatingResponse toResponse(Rating rating) {
        Listing listing = rating.getRentalRequest().getListing();
        return new RatingResponse(
                rating.getId(),
                rating.getOwner().getId(),
                rating.getOwner().getUsername(),
                rating.getRenter().getId(),
                rating.getRenter().getUsername(),
                rating.getRentalRequest().getId(),
                rating.getRatingValue(),
                rating.getComment(),
                rating.getCreatedAt()
        );
    }
}

