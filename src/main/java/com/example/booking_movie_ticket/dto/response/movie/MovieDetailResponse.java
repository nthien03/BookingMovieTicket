package com.example.booking_movie_ticket.dto.response.movie;
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

    private List<ActorInMovie> actors;

    private String description;

    private String poster;

    private String trailerUrl;

    private Integer duration;

    private List<GenreInMovie> genres;

    private Instant releaseDate;

    private Integer ageRestriction;

    private Boolean status;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActorInMovie {
        private Long id;
        private String fullName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GenreInMovie {
        private Long id;
        private String name;
    }
}
