package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.SeatTypeRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatTypeResponse;
import com.example.booking_movie_ticket.entity.SeatType;
import com.example.booking_movie_ticket.service.SeatTypeService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seat-types")
public class SeatTypeController {

    private final SeatTypeService seatTypeService;

    public SeatTypeController(SeatTypeService seatTypeService) {
        this.seatTypeService = seatTypeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(@Valid @RequestBody SeatTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Long>builder()
                        .code(1000)
                        .message("Seat type created successfully")
                        .data(seatTypeService.createSeatType(request))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAll(
            @Filter Specification<SeatType> spec,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.<PageResponse>builder()
                .code(1000)
                .data(seatTypeService.getAllSeatTypes(spec, pageable))
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatTypeResponse>> getById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.<SeatTypeResponse>builder()
                .code(1000)
                .data(seatTypeService.getSeatType(id))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> update(@PathVariable long id,
                                                    @Valid @RequestBody SeatTypeRequest request) {
        seatTypeService.updateSeatType(id, request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(1000)
                .message("Seat type updated successfully")
                .build());
    }
}

