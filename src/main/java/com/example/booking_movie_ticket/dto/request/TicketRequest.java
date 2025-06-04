package com.example.booking_movie_ticket.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TicketRequest {

    private Long price;
    private String ticketCode;
    private Boolean status;
    private Long scheduleId;
    private Long seatId;
    private Long bookingId;
}

