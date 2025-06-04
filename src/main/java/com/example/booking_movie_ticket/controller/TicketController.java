package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.TicketRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.booking.TicketResponse;
import com.example.booking_movie_ticket.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(@Valid @RequestBody TicketRequest request) {
        Long id = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<Long>builder()
                        .code(1000)
                        .message("Created")
                        .data(id)
                        .build()
        );
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(
                ApiResponse.<List<TicketResponse>>builder()
                        .code(1000)
                        .data(ticketService.getTicketsByBooking(bookingId))
                        .build());
    }

}

