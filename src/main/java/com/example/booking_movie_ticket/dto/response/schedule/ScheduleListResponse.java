package com.example.booking_movie_ticket.dto.response.schedule;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleListResponse {
    private Long id;
    private MovieInSchedule movie;
    private RoomInSchedule room;
    private Instant date;
    private Instant startTime;
    private Integer bufferTime;
    private Instant endTime;
    private Boolean status;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoomInSchedule {
        private Long id;
        private String roomName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MovieInSchedule {
        private Long id;
        private String movieName;
    }
}
