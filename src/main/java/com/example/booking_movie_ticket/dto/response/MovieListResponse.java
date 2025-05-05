package com.example.booking_movie_ticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieListResponse {
    private Long id;
    private String movieName;
    private String poster;
    private Integer duration;
    private Instant releaseDate;
}
