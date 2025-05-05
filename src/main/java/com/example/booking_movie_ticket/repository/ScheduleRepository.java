package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("""
    SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
    FROM Schedule s
    WHERE s.room.id = :roomId
      AND s.date = :date
      AND (
            (s.startTime < :endTime AND s.endTime > :startTime)
      )
""")
    boolean existsByRoomAndDateAndTimeOverlap(
            @Param("roomId") Long roomId,
            @Param("date") Instant date,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime
    );

}
