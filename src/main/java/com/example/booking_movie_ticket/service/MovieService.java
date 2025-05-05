package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.dto.response.MovieCreateResponse;
import com.example.booking_movie_ticket.dto.response.MovieListResponse;
import com.example.booking_movie_ticket.dto.response.MovieResponse;
import com.example.booking_movie_ticket.entity.Movie;

import java.util.List;

public interface MovieService {

    MovieCreateResponse createMovie(MovieRequest request);
    List<MovieListResponse> getAllMovies();
    MovieResponse getMovie(long movieId);
    MovieResponse updateMovie(long movieId, MovieRequest request);
    MovieResponse changeStatus(long movieid);

}
