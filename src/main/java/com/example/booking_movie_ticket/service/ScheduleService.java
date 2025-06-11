package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.ScheduleRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.room.RoomListResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleByMovieResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleCreateResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleListResponse;
import com.example.booking_movie_ticket.entity.Schedule;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface ScheduleService {
    void updateSchedule(Long scheduleId, ScheduleRequest request);

    //    List<ScheduleListResponse> getAllSchedules();
    List<Schedule> getAllSchedules();

    PageResponse getSchedules(Instant date, String movieName, Pageable pageable);
    ScheduleCreateResponse createSchedule(ScheduleRequest request);
    ScheduleListResponse getScheduleById(long scheduleId);

    List<RoomListResponse> getAvailableRooms(Instant startTime, Instant endTime);

    List<ScheduleByMovieResponse> getSchedulesByMovieId(Long movieId);

}
