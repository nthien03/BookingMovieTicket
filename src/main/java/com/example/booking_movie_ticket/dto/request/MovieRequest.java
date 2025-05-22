package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    @NotBlank(message = "MOVIE_NAME_BLANK")
    @Size(max = 255, message = "DATA_TOO_LONG")
    private String movieName;

    @NotBlank(message = "DIRECTOR_NAME_BLANK")
    @Size(max = 255, message = "DATA_TOO_LONG")
    private String director;

    private List<Long> actors;

    private String description;

    @NotBlank(message = "POSTER_BLANK")
    private String poster;

    @NotBlank(message = "TRAILER_URL_BLANK")
    @Size(max = 255, message = "DATA_TOO_LONG")
    private String trailerUrl;

    @NotNull
    private Integer duration;

    private List<Long> genres;

    @NotNull
    private Instant releaseDate;
    private Integer ageRestriction;
    private Boolean status;

}
