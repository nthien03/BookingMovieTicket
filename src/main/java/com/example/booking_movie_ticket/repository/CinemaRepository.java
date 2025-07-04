package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long>{
}
