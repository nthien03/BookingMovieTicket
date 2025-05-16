package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.GenreRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.GenreResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.entity.Genre;
import com.example.booking_movie_ticket.service.GenreService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createGenre(@Valid @RequestBody GenreRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<Long>builder()
                        .code(1000)
                        .message("Genre created successfully")
                        .data(genreService.createGenre(request)).build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAllGenres(
            @Filter Specification<Genre> spec,
            Pageable pageable) {


        return ResponseEntity.ok().body(
                ApiResponse.<PageResponse>builder()
                        .code(1000)
                        .data(genreService.getAllGenres(spec, pageable))
                        .build());
    }

    @GetMapping("/{genreId}")
    public ResponseEntity<ApiResponse<GenreResponse>> getGenre(@PathVariable long genreId) {

        return ResponseEntity.ok().body(
                ApiResponse.<GenreResponse>builder()
                        .code(1000)
                        .data(genreService.getGenre(genreId))
                        .build());
    }

    @PutMapping("/{genreId}")
    public ResponseEntity<ApiResponse<Void>> updateGenre(@PathVariable long genreId, @Valid @RequestBody GenreRequest request) {
        this.genreService.updateGenre(genreId, request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(1000)
                .message("Genre updated successfully")
                .build());
    }


}
