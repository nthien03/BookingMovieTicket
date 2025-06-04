package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long>, JpaSpecificationExecutor<SeatType> {
    boolean existsBySeatTypeNameIgnoreCase(String seatTypeName);
    Optional<SeatType> findBySeatTypeNameIgnoreCase(String seatTypeName);
}
