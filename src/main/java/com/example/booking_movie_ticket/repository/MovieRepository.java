package com.example.booking_movie_ticket.repository;

import com.example.booking_movie_ticket.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    boolean existsByMovieName(String movieName);

    /**
     * Phim đang chiếu =  đã kho chiếu + có ít nhất 1 suất chiếu còn mở bán kể từ NOW.
     */

//    @Query("""
//           SELECT DISTINCT m
//           FROM Movie m
//           WHERE m.releaseDate <= :now
//             AND m.status = TRUE
//             AND EXISTS (
//                  SELECT 1
//                  FROM Schedule sch
//                  WHERE sch.movie = m
//                    AND sch.status = TRUE
//                    AND sch.startTime >= :now
//             )
//           """)
//    List<Movie> findNowShowing(@Param("now") Instant now);

    @Query("""
       SELECT DISTINCT m
       FROM Movie m
       WHERE m.releaseDate <= :now
         AND m.status = TRUE
         AND EXISTS (
              SELECT 1
              FROM Schedule sch
              WHERE sch.movie = m
                AND sch.status = TRUE
                AND sch.startTime > :now
         )
         AND (:kw IS NULL OR LOWER(m.movieName) LIKE LOWER(CONCAT('%', :kw, '%')))
       ORDER BY m.releaseDate DESC
       """)
    List<Movie> searchNowShowingByName(@Param("now") Instant now,
                                       @Param("kw") String keyword);


    List<Movie> findByReleaseDateAfterAndStatusIsTrueOrderByReleaseDateAsc(Instant now);

    @Query("SELECT DISTINCT s.movie FROM Schedule s " +
            "WHERE s.date BETWEEN :fromDate AND :toDate " +
            "AND s.startTime > :now " +
            "AND s.status = true")
    List<Movie> findMoviesWithUpcomingSchedules(@Param("fromDate") Instant fromDate,
                                                            @Param("toDate") Instant toDate,
                                                            @Param("now") Instant now);

    Optional<Movie> findByMovieName(String movieName);
}
