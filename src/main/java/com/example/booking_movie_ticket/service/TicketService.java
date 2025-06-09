package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.TicketRequest;
import com.example.booking_movie_ticket.dto.response.booking.TicketResponse;

import java.util.List;

public interface TicketService {
    Long createTicket(TicketRequest request);
    List<TicketResponse> getTicketsByBooking(Long bookingId);

    void updateTicketStatusByBookingId(Long bookingId, Boolean newStatus);
}

