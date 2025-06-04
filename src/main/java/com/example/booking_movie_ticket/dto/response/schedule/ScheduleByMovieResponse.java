package com.example.booking_movie_ticket.dto.response.schedule;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleByMovieResponse {
    private Long id;
    private RoomInSchedule room;
    private Instant date;
    private Instant startTime;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoomInSchedule {
        private Long id;
        private String roomName;
    }
}
