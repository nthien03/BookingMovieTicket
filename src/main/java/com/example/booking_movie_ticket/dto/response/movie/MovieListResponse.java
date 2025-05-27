package com.example.booking_movie_ticket.dto.response.movie;

import lombok.*;

import java.time.Instant;

@Setter
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
