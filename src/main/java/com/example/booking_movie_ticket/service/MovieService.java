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


import java.util.List;

public interface MovieService {

    MovieCreateResponse createMovie(MovieRequest request);
    PageResponse getAllMovies(Specification<Movie> spec, Pageable pageable);
    MovieDetailResponse getMovieById(long movieId);
    MovieResponse updateMovie(long movieId, MovieRequest request);

    List<MovieListResponse> searchNowShowing(String keyword);
    List<MovieListResponse> getComingSoonMovies();
    MovieResponse changeStatus(long movieid);

}
