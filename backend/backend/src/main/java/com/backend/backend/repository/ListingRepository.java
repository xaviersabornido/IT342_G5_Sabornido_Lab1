package com.backend.backend.repository;

import com.backend.backend.entity.Listing;
import com.backend.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingRepository extends JpaRepository<Listing, Long> {

    List<Listing> findByOwner(User owner);
}

