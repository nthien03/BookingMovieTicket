package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.dto.response.seat.SeatStatusResponse;
import com.example.booking_movie_ticket.entity.Booking;
import com.example.booking_movie_ticket.util.constant.BookingStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    List<Booking> findByUserId(Long userId);

    @EntityGraph(attributePaths = {
            "tickets.schedule.movie",
            "tickets.schedule.room",
            "tickets.seat"
    })
    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    Optional<Booking> findBookingWithDetails(@Param("id") Long id);


    @EntityGraph(attributePaths = {"tickets.schedule.movie"})
    Page<Booking> findByUserIdAndTicketsScheduleMovieMovieNameContainingIgnoreCaseOrderByBookingDateDesc(
            Long userId, String movieName, Pageable pageable);

    @EntityGraph(attributePaths = {"tickets.schedule.movie"})
    Page<Booking> findByUserIdOrderByBookingDateDesc(Long userId, Pageable pageable);

    boolean existsByBookingCode(String bookingCode);

    Optional<Booking> findByBookingCode(String bookingCode);

    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.status = :newStatus " +
            "WHERE b.status = :currentStatus AND b.bookingDate < :expire")
    int updateStatusForExpiredBookings(@Param("newStatus") int newStatus,
                                       @Param("currentStatus") int currentStatus,
                                       @Param("expire") Instant expire);


//    // Kiểm tra 1 ghế đã được đặt cho schedule cụ thể chưa
//    @Query("""
//        SELECT COUNT(b) > 0
//        FROM Booking b
//        JOIN b.tickets t
//        WHERE t.seatId = :seatId
//        AND t.scheduleId = :scheduleId
//        AND b.status IN (1, 4)
//        """)
//    boolean isSeatBookedForSchedule(@Param("seatId") Long seatId,
//                                    @Param("scheduleId") Long scheduleId);
//
//    // Lấy danh sách ghế đã đặt cho 1 schedule
//    @Query("""
//        SELECT t.seatId
//        FROM Booking b
//        JOIN b.tickets t
//        WHERE t.scheduleId = :scheduleId
//        AND b.status IN (1, 4)
//        """)
//    List<Long> getBookedSeatIds(@Param("scheduleId") Long scheduleId);

//    // Kiểm tra nhiều ghế cùng lúc
//    @Query("""
//        SELECT t.seatId
//        FROM Booking b
//        JOIN b.tickets t
//        WHERE t.seatId IN :seatIds
//        AND t.scheduleId = :scheduleId
//        AND b.status IN (1, 4)
//        """)
//    List<Long> getBookedSeatsFromList(@Param("seatIds") List<Long> seatIds,
//                                      @Param("scheduleId") Long scheduleId);


}
