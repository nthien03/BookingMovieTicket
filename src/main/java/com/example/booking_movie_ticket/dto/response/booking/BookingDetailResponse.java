package com.example.booking_movie_ticket.dto.response.booking;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetailResponse {
    private Long id;
    private Long totalPrice;
    private String bookingCode;
    private Integer amount;
    private Boolean status;
    private Instant bookingDate;
    private Long userId;

    private List<TicketResponse> tickets;
    private PaymentResponse payment;
}
