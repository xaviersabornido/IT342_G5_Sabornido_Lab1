package com.backend.backend.repository;

import com.backend.backend.entity.Listing;
import com.backend.backend.entity.ListingPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListingPhotoRepository extends JpaRepository<ListingPhoto, Long> {

    List<ListingPhoto> findByListingOrderBySortOrderAscIdAsc(Listing listing);

    void deleteByListing(Listing listing);
}

