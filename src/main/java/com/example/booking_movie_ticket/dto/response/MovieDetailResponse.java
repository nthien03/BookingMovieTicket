package com.example.booking_movie_ticket.dto.response;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailResponse {

    private Long id;

    private String movieName;

    private String director;

    private List<String> actors;

    private String description;

    private String poster;

    private String trailerUrl;

    private Integer duration;

    private List<String> genres;

    private Instant releaseDate;

    private Integer ageRestriction;

    private Boolean status;


}
