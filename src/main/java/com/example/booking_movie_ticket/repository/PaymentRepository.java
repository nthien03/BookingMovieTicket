package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByBookingId(Long bookingId);
    Optional<Payment> findByBookingId(Long bookingId);
}
