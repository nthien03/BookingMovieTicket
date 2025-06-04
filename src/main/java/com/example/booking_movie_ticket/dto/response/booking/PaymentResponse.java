package com.example.booking_movie_ticket.dto.response.booking;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private String method;
    private String paymentCode;
    private Instant paymentDate;
    private Long amount;
    private Boolean status;
    private Long bookingId;
}

