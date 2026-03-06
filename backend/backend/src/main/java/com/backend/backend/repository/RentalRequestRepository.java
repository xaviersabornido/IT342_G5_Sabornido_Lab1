package com.backend.backend.repository;

import com.backend.backend.entity.RentalRequest;
import com.backend.backend.entity.Listing;
import com.backend.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRequestRepository extends JpaRepository<RentalRequest, Long> {

    List<RentalRequest> findByListing(Listing listing);

    List<RentalRequest> findByRenter(User renter);

    List<RentalRequest> findByListingOwner(User owner);
}

