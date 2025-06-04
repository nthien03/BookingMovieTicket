package com.example.booking_movie_ticket.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingRequest {
    private Long totalPrice;
    private String bookingCode;
    private Integer amount;
    private Boolean status;
    private Long userId;
}

