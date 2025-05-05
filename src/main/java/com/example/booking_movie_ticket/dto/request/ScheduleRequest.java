package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ScheduleRequest {
    @NotNull(message = "Movie ID cannot be null")
    private Long movieId;

    @NotNull(message = "Room ID cannot be null")
    private Long roomId;

    @NotNull(message = "Date cannot be null")
    private Instant date;

    @NotNull(message = "Start time cannot be null")
    private Instant startTime;

    @NotNull(message = "End time cannot be null")
    private Instant endTime;

    @NotNull(message = "Status cannot be null")
    private Boolean status;
}
