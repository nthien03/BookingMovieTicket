package com.example.booking_movie_ticket.dto.response.booking;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingCreateResponse {
    private Long id;
    private String bookingCode;
}
