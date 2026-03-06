package com.backend.backend.service;

import com.backend.backend.dto.RentalRequestCreateRequest;
import com.backend.backend.dto.RentalRequestResponse;
import com.backend.backend.entity.Listing;
import com.backend.backend.entity.RentalRequest;
import com.backend.backend.entity.User;
import com.backend.backend.exception.InvalidCredentialsException;
import com.backend.backend.repository.ListingRepository;
import com.backend.backend.repository.RentalRequestRepository;
import com.backend.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalRequestService {

    private final RentalRequestRepository rentalRequestRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public RentalRequestService(RentalRequestRepository rentalRequestRepository,
                                ListingRepository listingRepository,
                                UserRepository userRepository) {
        this.rentalRequestRepository = rentalRequestRepository;
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RentalRequestResponse createRequest(String renterUsername, RentalRequestCreateRequest request) {
        User renter = userRepository.findByUsername(renterUsername)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        Listing listing = listingRepository.findById(request.getListingId())
                .orElseThrow(() -> new InvalidCredentialsException("Listing not found"));

        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setListing(listing);
        rentalRequest.setRenter(renter);

        RentalRequest saved = rentalRequestRepository.save(rentalRequest);
        return toResponse(saved);
    }

    public List<RentalRequestResponse> getRequestsForRenter(String renterUsername) {
        User renter = userRepository.findByUsername(renterUsername)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        return rentalRequestRepository.findByRenter(renter).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<RentalRequestResponse> getRequestsForOwner(String ownerUsername) {
        User owner = userRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        return rentalRequestRepository.findByListingOwner(owner).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RentalRequestResponse approveRequest(String ownerUsername, Long requestId) {
        RentalRequest rentalRequest = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new InvalidCredentialsException("Request not found"));

        if (!rentalRequest.getListing().getOwner().getUsername().equals(ownerUsername)) {
            throw new InvalidCredentialsException("You are not allowed to approve this request");
        }

        rentalRequest.setStatus("APPROVED");
        RentalRequest saved = rentalRequestRepository.save(rentalRequest);
        return toResponse(saved);
    }

    @Transactional
    public RentalRequestResponse declineRequest(String ownerUsername, Long requestId) {
        RentalRequest rentalRequest = rentalRequestRepository.findById(requestId)
                .orElseThrow(() -> new InvalidCredentialsException("Request not found"));

        if (!rentalRequest.getListing().getOwner().getUsername().equals(ownerUsername)) {
            throw new InvalidCredentialsException("You are not allowed to decline this request");
        }

        rentalRequest.setStatus("DECLINED");
        RentalRequest saved = rentalRequestRepository.save(rentalRequest);
        return toResponse(saved);
    }

    private RentalRequestResponse toResponse(RentalRequest rentalRequest) {
        return new RentalRequestResponse(
                rentalRequest.getId(),
                rentalRequest.getListing().getId(),
                rentalRequest.getListing().getTitle(),
                rentalRequest.getRenter().getId(),
                rentalRequest.getRenter().getUsername(),
                rentalRequest.getStatus(),
                rentalRequest.getRequestedAt(),
                rentalRequest.getUpdatedAt()
        );
    }
}

