package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.GenreRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.GenreResponse;
import com.example.booking_movie_ticket.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genres")
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

}
