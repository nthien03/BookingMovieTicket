package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    List<Booking> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"tickets.schedule.movie"})
    Page<Booking> findByUserIdAndTicketsScheduleMovieMovieNameContainingIgnoreCaseOrderByBookingDateDesc(
            Long userId, String movieName, Pageable pageable);

    @EntityGraph(attributePaths = {"tickets.schedule.movie"})
    Page<Booking> findByUserIdOrderByBookingDateDesc(Long userId, Pageable pageable);
}
