package com.example.booking_movie_ticket.dto.response.booking;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private Long id;
    private Long totalPrice;
    private String bookingCode;
    private Integer amount;
    private Boolean status;
    private Instant bookingDate;
    private UserInBooking user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInBooking {
        private Long id;
        private String username;
    }
}

