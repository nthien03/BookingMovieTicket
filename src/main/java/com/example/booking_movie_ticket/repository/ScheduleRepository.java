package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {


    @Query("SELECT s FROM Schedule s " +
            "WHERE (:date IS NULL OR s.date = :date) " +
            "AND (:movieName IS NULL OR s.movie.movieName LIKE %:movieName%) ")
    Page<Schedule> findSchedulesByDateAndMovieName(
            @Param("date") Instant date,
            @Param("movieName") String movieName,
            Pageable pageable);


    //List<Schedule> findByMovieIdAndStatusTrue(Long movieId);
    List<Schedule> findByMovieIdAndStatusTrueAndDateGreaterThanEqual(Long movieId, Instant date);


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

    @Query("""
                SELECT s.room.id FROM Schedule s
                WHERE (:startTime < s.endTime AND :endTime > s.startTime)
            """)
    List<Long> findRoomIdsWithScheduleConflict(
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime
    );

}
