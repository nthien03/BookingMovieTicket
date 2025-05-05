package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

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

    @Size(max = 255, message = "DATA_TOO_LONG")
    private String actors;

    private String description;

    //@NotBlank(message = "POSTER_BLANK")
    private String poster;

    @NotBlank(message = "TRAILER_URL_BLANK")
    private String trailerUrl;
    private Integer duration;

    @NotBlank(message = "GENRE_BLANK")
    @Size(max = 255, message = "DATA_TOO_LONG")
    private String genre;

    //@FutureOrPresent(message = "RELEASE_DATE_INVALID")
    private Instant releaseDate;
    private Integer ageRestriction;

}
