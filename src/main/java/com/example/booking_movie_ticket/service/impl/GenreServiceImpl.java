package com.example.booking_movie_ticket.service.impl;


import com.example.booking_movie_ticket.dto.request.GenreRequest;
import com.example.booking_movie_ticket.dto.response.GenreResponse;
import com.example.booking_movie_ticket.entity.Genre;
import com.example.booking_movie_ticket.repository.GenreRepository;
import com.example.booking_movie_ticket.service.GenreService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public long createGenre(GenreRequest request) {
        Genre genre = Genre.builder()
                .name(request.getName())
                .description(request.getDescription())
                .status(true)
                .build();
        Genre savedgenre = genreRepository.save(genre);
        return savedgenre.getId();

    }

    @Override
    public List<GenreResponse> getAllGenres() {
        return List.of();
    }

    @Override
    public GenreResponse getGenre(long genreId) {
        return null;
    }

    @Override
    public GenreResponse updateGenre(long genreId, GenreRequest request) {
        return null;
    }

    @Override
    public GenreResponse changeStatus(long genreId) {
        return null;
    }
}
