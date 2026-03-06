package com.backend.backend.service;

import com.backend.backend.dto.ListingRequest;
import com.backend.backend.dto.ListingResponse;
import com.backend.backend.entity.Listing;
import com.backend.backend.entity.User;
import com.backend.backend.exception.InvalidCredentialsException;
import com.backend.backend.repository.ListingRepository;
import com.backend.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository listingRepository, UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.userRepository = userRepository;
    }

    public List<ListingResponse> getAllListings() {
        return listingRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ListingResponse getListing(Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new InvalidCredentialsException("Listing not found"));
        return toResponse(listing);
    }

    @Transactional
    public ListingResponse createListing(String ownerUsername, ListingRequest request) {
        User owner = userRepository.findByUsername(ownerUsername)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        Listing listing = new Listing();
        listing.setOwner(owner);
        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setPrice(request.getPrice());
        listing.setLocation(request.getLocation());
        listing.setPropertyType(request.getPropertyType());
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            listing.setStatus(request.getStatus());
        }

        Listing saved = listingRepository.save(listing);
        return toResponse(saved);
    }

    @Transactional
    public ListingResponse updateListing(String ownerUsername, Long id, ListingRequest request) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new InvalidCredentialsException("Listing not found"));

        if (!listing.getOwner().getUsername().equals(ownerUsername)) {
            throw new InvalidCredentialsException("You are not allowed to modify this listing");
        }

        listing.setTitle(request.getTitle());
        listing.setDescription(request.getDescription());
        listing.setPrice(request.getPrice());
        listing.setLocation(request.getLocation());
        listing.setPropertyType(request.getPropertyType());
        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            listing.setStatus(request.getStatus());
        }

        Listing saved = listingRepository.save(listing);
        return toResponse(saved);
    }

    @Transactional
    public void deleteListing(String ownerUsername, Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new InvalidCredentialsException("Listing not found"));

        if (!listing.getOwner().getUsername().equals(ownerUsername)) {
            throw new InvalidCredentialsException("You are not allowed to delete this listing");
        }

        listingRepository.delete(listing);
    }

    private ListingResponse toResponse(Listing listing) {
        return new ListingResponse(
                listing.getId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getPrice(),
                listing.getLocation(),
                listing.getPropertyType(),
                listing.getStatus(),
                listing.getOwner().getId(),
                listing.getOwner().getUsername(),
                listing.getCreatedAt(),
                listing.getUpdatedAt()
        );
    }
}

