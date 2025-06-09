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
    private Integer status;
    private Instant bookingDate;
    private String movieName;
    private String roomName;
    private Instant date;
    private Instant startTime;
    private UserInfoInBooking user;
    private List<TicketInBookingResponse> tickets;
    private String paymentMethod;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfoInBooking {
        private Long id;
        private String username;
        private String fullName;
    }
}
