package com.example.booking_movie_ticket.dto.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleByMovieResponse {
    private Long id;
    private String roomName;
    private Instant date;
    private Instant startTime;
}
