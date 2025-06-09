package com.example.booking_movie_ticket.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookingStatus {

    DRAFT(0),
    BOOKED(1),
    COMPLETED(2),
    CANCELLED(3),
    PAYMENT_PROCESSING(4),
    PAYMENT_FAILED(5)
    ;

    private final int value;
}
