package com.backend.backend.controller;

import com.backend.backend.dto.RentalRequestCreateRequest;
import com.backend.backend.dto.RentalRequestResponse;
import com.backend.backend.service.RentalRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RentalRequestController {

    private final RentalRequestService rentalRequestService;

    public RentalRequestController(RentalRequestService rentalRequestService) {
        this.rentalRequestService = rentalRequestService;
    }

    @PostMapping
    public ResponseEntity<RentalRequestResponse> createRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody RentalRequestCreateRequest request
    ) {
        RentalRequestResponse response = rentalRequestService.createRequest(userDetails.getUsername(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<List<RentalRequestResponse>> getUserRequests(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(rentalRequestService.getRequestsForRenter(userDetails.getUsername()));
    }

    @GetMapping("/owner")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<RentalRequestResponse>> getOwnerRequests(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(rentalRequestService.getRequestsForOwner(userDetails.getUsername()));
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RentalRequestResponse> approveRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        RentalRequestResponse response = rentalRequestService.approveRequest(userDetails.getUsername(), id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/decline")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<RentalRequestResponse> declineRequest(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        RentalRequestResponse response = rentalRequestService.declineRequest(userDetails.getUsername(), id);
        return ResponseEntity.ok(response);
    }
}

