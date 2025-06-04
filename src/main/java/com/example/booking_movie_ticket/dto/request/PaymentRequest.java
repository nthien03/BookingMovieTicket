package com.example.booking_movie_ticket.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String method;
    private String paymentCode;
    private Long amount;
    private Boolean status;
    private Long bookingId;
}

