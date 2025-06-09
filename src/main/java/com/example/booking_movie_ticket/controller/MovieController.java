package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.dto.response.*;
import com.example.booking_movie_ticket.dto.response.movie.MovieCreateResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieDetailResponse;
import com.example.booking_movie_ticket.dto.response.movie.MovieListResponse;
import com.example.booking_movie_ticket.entity.Movie;
import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.service.MovieService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MovieCreateResponse>> createMovie(@Valid @RequestBody MovieRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<MovieCreateResponse>builder()
                        .code(1000)
                        .message("Movie created successfully")
                        .data(movieService.createMovie(request)).build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAllMovies(
            @Filter Specification<Movie> spec,
            Pageable pageable) {

        return ResponseEntity.ok().body(
                ApiResponse.<PageResponse>builder()
                        .code(1000)
                        .data(movieService.getAllMovies(spec, pageable))
                        .build());
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<ApiResponse<MovieDetailResponse>> getMovie(@PathVariable long movieId) {
        return ResponseEntity.ok().body(
                ApiResponse.<MovieDetailResponse>builder()
                        .code(1000)
                        .data(movieService.getMovieById(movieId))
                        .build());
    }

    @GetMapping("/now-showing")
    public ResponseEntity<ApiResponse<List<MovieListResponse>>> getNowShowing(
            @RequestParam(value = "keyword", required = false) String keyword) {
        return ResponseEntity.ok().body(
                ApiResponse.<List<MovieListResponse>>builder()
                        .code(1000)
                        .data(movieService.searchNowShowing(keyword))
                        .build());

    }

    @GetMapping("/coming-soon")
    public ResponseEntity<ApiResponse<List<MovieListResponse>>> getComingSoonMovies() {
        return ResponseEntity.ok().body(
                ApiResponse.<List<MovieListResponse>>builder()
                        .code(1000)
                        .data(movieService.getComingSoonMovies())
                        .build());
    }
}
