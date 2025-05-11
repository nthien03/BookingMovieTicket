package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.ActorRequest;
import com.example.booking_movie_ticket.dto.request.GenreRequest;
import com.example.booking_movie_ticket.dto.response.ActorCreateResponse;
import com.example.booking_movie_ticket.dto.response.ActorResponse;
import com.example.booking_movie_ticket.dto.response.ActorUpdateResponse;
import com.example.booking_movie_ticket.dto.response.GenreResponse;

import java.util.List;

public interface GenreService {
    long createGenre(GenreRequest request);
    List<GenreResponse> getAllGenres();
    GenreResponse getGenre(long genreId);
    GenreResponse updateGenre(long genreId, GenreRequest request);
    GenreResponse changeStatus(long genreId);
}
