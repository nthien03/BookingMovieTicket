package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.dto.response.seat.SeatStatusResponse;
import com.example.booking_movie_ticket.entity.Seat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Seat> {

    @EntityGraph(attributePaths = {"seatType"})
    List<Seat> findByRoomIdAndStatusTrue(Long roomId);

    @EntityGraph(attributePaths = {"seatType"})
    List<Seat> findByRoomId(Long roomId); // backup cho admin view

    List<Seat> findByIdInAndStatusTrue(List<Long> seatIds);

    @Query("""
                SELECT new com.example.booking_movie_ticket.dto.response.seat.SeatStatusResponse(
                    s.id,
                    s.seatRow,
                    s.seatNumber,
                    s.price,
                    s.seatType.seatTypeName,
                    CASE
                        WHEN EXISTS (
                            SELECT 1
                            FROM Ticket t
                            JOIN t.booking b
                            WHERE t.seat.id = s.id
                              AND t.schedule.id = :scheduleId
                              AND b.status IN :bookingStatusValues
                        )
                        THEN true
                        ELSE false
                    END
                )
                FROM Seat s
                WHERE s.room.id = :roomId
                  AND s.status = true
                ORDER BY s.seatRow, s.seatNumber
            """)
    List<SeatStatusResponse> findSeatStatusesByRoomAndSchedule(
            Long roomId,
            Long scheduleId,
            @Param("bookingStatusValues") List<Integer> bookingStatusValues);

//    @Query("SELECT new com.example.booking_movie_ticket.dto.response.seat.SeatStatusResponse(" +
//            "s.id, s.seatRow, s.seatNumber, " +
//            "CASE WHEN EXISTS (" +
//            "SELECT 1 FROM Ticket t JOIN t.booking b " +
//            "WHERE t.seat.id = s.id AND t.schedule.id = :scheduleId " +
//            "AND b.status IN :bookingStatusValues" +
//            ") THEN true ELSE false END) " +
//            "FROM Seat s " +
//            "WHERE s.room.id = :roomId AND s.status = true " +
//            "ORDER BY s.seatRow, s.seatNumber")
//    List<SeatStatusResponse> findSeatStatusesByRoomAndSchedule(
//            @Param("roomId") Long roomId,
//            @Param("scheduleId") Long scheduleId,
//            @Param("bookingStatusValues") List<Integer> bookingStatusValues  // Changed from List<String> to List<Integer>
//    );


    boolean existsBySeatRowIgnoreCaseAndSeatNumberAndRoom_Id(String seatRow, Integer seatNumber, Long roomId);
}

