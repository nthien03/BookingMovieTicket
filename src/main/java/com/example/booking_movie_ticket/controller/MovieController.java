package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.request.MovieRequest;
import com.example.booking_movie_ticket.dto.response.*;
import com.example.booking_movie_ticket.service.MovieService;
import jakarta.validation.Valid;
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
    public List<MovieListResponse> getAllMovies() {
        return movieService.getAllMovies();
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
}
