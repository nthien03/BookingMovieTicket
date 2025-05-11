package com.example.booking_movie_ticket.mapper;

import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.entity.Movie;

import java.time.Instant;

public class MovieMapper {

    public static Movie toEntity(MovieRequest request) {
        return Movie.builder()
                .movieName(request.getMovieName())
                .actor(request.getActors())
                .director(request.getDirector())
                .description(request.getDescription())
                .poster(request.getPoster())
                .trailerUrl(request.getTrailerUrl())
                .duration(request.getDuration())
                .genre(request.getGenre())
                .releaseDate(request.getReleaseDate())
                .ageRestriction(request.getAgeRestriction())
                .status(true)
                .createdAt(Instant.now())
                .build();
    }


}
