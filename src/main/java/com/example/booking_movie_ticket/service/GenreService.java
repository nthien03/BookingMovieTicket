package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.GenreRequest;
import com.example.booking_movie_ticket.dto.response.genre.GenreResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.entity.Genre;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface GenreService {
    long createGenre(GenreRequest request);
    PageResponse getAllGenres(Specification<Genre> spec, Pageable pageable);
    GenreResponse getGenre(long genreId);
    void updateGenre(long genreId, GenreRequest request);
    GenreResponse changeStatus(long genreId);
}
