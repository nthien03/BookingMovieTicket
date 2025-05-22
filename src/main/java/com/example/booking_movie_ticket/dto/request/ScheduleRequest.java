package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ScheduleRequest {
    @NotNull
    private Long movieId;

    @NotNull
    private Long roomId;

    @NotNull
    private Instant date;

    @NotNull
    private Instant startTime;

    @NotNull
    private Integer bufferTime;

    @NotNull
    private Instant endTime;

    @NotNull
    private Boolean status;

}
