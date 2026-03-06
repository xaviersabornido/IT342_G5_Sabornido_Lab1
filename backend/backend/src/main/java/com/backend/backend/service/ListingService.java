package com.backend.backend.service;

import com.backend.backend.dto.ListingRequest;
import com.backend.backend.dto.ListingResponse;
import com.backend.backend.entity.Listing;
import com.backend.backend.entity.ListingPhoto;
import com.backend.backend.entity.User;
import com.backend.backend.exception.InvalidCredentialsException;
import com.backend.backend.repository.ListingPhotoRepository;
import com.backend.backend.repository.ListingRepository;
import com.backend.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final ListingPhotoRepository listingPhotoRepository;
    private final UserRepository userRepository;

    public ListingService(ListingRepository listingRepository,
                          ListingPhotoRepository listingPhotoRepository,
                          UserRepository userRepository) {
        this.listingRepository = listingRepository;
        this.listingPhotoRepository = listingPhotoRepository;
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

        if (request.getPhotoUrls() != null && !request.getPhotoUrls().isEmpty()) {
            saveListingPhotos(saved, request.getPhotoUrls());
        }

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

        if (request.getPhotoUrls() != null) {
            listingPhotoRepository.deleteByListing(saved);
            if (!request.getPhotoUrls().isEmpty()) {
                saveListingPhotos(saved, request.getPhotoUrls());
            }
        }

        return toResponse(saved);
    }

    @Transactional
    public void deleteListing(String ownerUsername, Long id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new InvalidCredentialsException("Listing not found"));

        if (!listing.getOwner().getUsername().equals(ownerUsername)) {
            throw new InvalidCredentialsException("You are not allowed to delete this listing");
        }

        listingPhotoRepository.deleteByListing(listing);
        listingRepository.delete(listing);
    }

    private ListingResponse toResponse(Listing listing) {
        java.util.List<ListingPhoto> photos =
                listingPhotoRepository.findByListingOrderBySortOrderAscIdAsc(listing);

        java.util.List<String> photoUrls = photos.stream()
                .map(ListingPhoto::getUrl)
                .collect(java.util.stream.Collectors.toList());

        return new ListingResponse(
                listing.getId(),
                listing.getTitle(),
                listing.getDescription(),
                listing.getPrice(),
                listing.getLocation(),
                photoUrls,
                listing.getPropertyType(),
                listing.getStatus(),
                listing.getOwner().getId(),
                listing.getOwner().getUsername(),
                listing.getCreatedAt(),
                listing.getUpdatedAt()
        );
    }

    private void saveListingPhotos(Listing listing, java.util.List<String> photoUrls) {
        java.util.List<ListingPhoto> photos = new java.util.ArrayList<>();
        for (int i = 0; i < photoUrls.size(); i++) {
            String url = photoUrls.get(i);
            if (url == null || url.isBlank()) {
                continue;
            }
            ListingPhoto photo = new ListingPhoto();
            photo.setListing(listing);
            photo.setUrl(url);
            photo.setPrimary(i == 0);
            photo.setSortOrder(i);
            photos.add(photo);
        }
        if (!photos.isEmpty()) {
            listingPhotoRepository.saveAll(photos);
        }
    }
}

