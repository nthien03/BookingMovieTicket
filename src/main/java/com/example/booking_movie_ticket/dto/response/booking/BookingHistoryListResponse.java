package com.example.booking_movie_ticket.dto.response.booking;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingHistoryListResponse {
    private Long id;
    private String bookingCode;
    private Instant bookingDate;
    private String movieName;
    private Long totalPrice;

}
