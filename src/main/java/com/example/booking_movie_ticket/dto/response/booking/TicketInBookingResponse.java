package com.example.booking_movie_ticket.dto.response.booking;

import com.example.booking_movie_ticket.dto.response.schedule.ScheduleByMovieResponse;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketInBookingResponse {
    private Long id;
    private Long price;
    private SeatInBooking seat;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeatInBooking {
        private Long id;
        private String seatRow;
        private Integer seatNumber;
        private Integer price;
    }
}

