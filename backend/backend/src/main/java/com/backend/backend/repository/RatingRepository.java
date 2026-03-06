package com.backend.backend.repository;

import com.backend.backend.entity.Rating;
import com.backend.backend.entity.RentalRequest;
import com.backend.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByOwner(User owner);

    List<Rating> findByRenter(User renter);

    Optional<Rating> findByRentalRequest(RentalRequest rentalRequest);
}

