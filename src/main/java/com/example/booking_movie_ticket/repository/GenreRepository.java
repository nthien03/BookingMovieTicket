package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {
    boolean existsByName(String name);
    List<Genre> findByIdIn(List<Long> id);
}
