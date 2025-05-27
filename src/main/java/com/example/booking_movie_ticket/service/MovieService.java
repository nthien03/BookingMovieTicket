package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.dto.response.movie.MovieCreateResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieDetailResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieListResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieResponse;


import java.util.List;

public interface MovieService {

    MovieCreateResponse createMovie(MovieRequest request);
    List<MovieListResponse> getAllMovies();
    MovieDetailResponse getMovieById(long movieId);
    MovieResponse updateMovie(long movieId, MovieRequest request);

    List<MovieListResponse> searchNowShowing(String keyword);

    MovieResponse changeStatus(long movieid);

}
