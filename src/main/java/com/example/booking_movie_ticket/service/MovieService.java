package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieCreateResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieDetailResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieListResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieResponse;
import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Permission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MovieService {

    MovieCreateResponse createMovie(MovieRequest request);
    PageResponse getAllMovies(Specification<Movie> spec, Pageable pageable);
    MovieDetailResponse getMovieById(long movieId);
    void updateMovie(long movieId, MovieRequest request);

    List<MovieListResponse> searchNowShowing(String keyword);
    List<MovieListResponse> getComingSoonMovies();
    Map<LocalDate, List<MovieListResponse>> getUpcomingMoviesWithSchedules();

    MovieResponse changeStatus(long movieid);

}
