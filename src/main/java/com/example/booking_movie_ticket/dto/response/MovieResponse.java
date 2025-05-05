package com.example.booking_movie_ticket.dto.response;

import java.time.LocalDate;

public class MovieResponse {
    private Long id;
    private String movieName;
    private String director;
    private String actors;
    private String description;
    private String poster;
    private String trailerUrl;
    private Integer duration;
    private String genre;
    private LocalDate releaseDate;
    private Integer ageRestriction;
    private Boolean status;
}
