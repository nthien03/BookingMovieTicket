package com.example.booking_movie_ticket.dto.response.booking;

import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    private Long price;
    private String ticketCode;
    private Boolean status;
    private Long scheduleId;
    private Long seatId;
    private Long bookingId;
    private Instant createdAt;
}

