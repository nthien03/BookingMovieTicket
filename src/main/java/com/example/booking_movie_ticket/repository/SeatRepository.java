package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {
    List<Seat> findByRoomId(Long roomId);
    boolean existsBySeatRowIgnoreCaseAndSeatNumberAndRoom_Id(String seatRow, Integer seatNumber, Long roomId);
}

