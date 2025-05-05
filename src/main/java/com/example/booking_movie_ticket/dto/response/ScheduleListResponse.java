package com.example.booking_movie_ticket.dto.response;

import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Room;
import jakarta.persistence.*;

import java.time.Instant;

public class ScheduleListResponse {
    private Long id;
    private Long movieId;
    private Long roomId;
    private Instant date;
    private Instant startTime;
    private Instant endTime;
    private Boolean status;
}
