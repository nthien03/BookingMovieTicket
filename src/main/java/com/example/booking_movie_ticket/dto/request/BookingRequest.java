package com.example.booking_movie_ticket.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingRequest {

    private Long totalPrice;
    private Integer amount;
    private Long userId;
    private String paymentMethod;
}

