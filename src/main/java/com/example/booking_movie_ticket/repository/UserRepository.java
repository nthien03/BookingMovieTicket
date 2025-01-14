package com.example.booking_movie_ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.booking_movie_ticket.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
