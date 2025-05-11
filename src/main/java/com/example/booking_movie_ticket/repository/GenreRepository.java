package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
