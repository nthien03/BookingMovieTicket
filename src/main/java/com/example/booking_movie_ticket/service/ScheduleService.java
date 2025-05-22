package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.ScheduleRequest;
import com.example.booking_movie_ticket.dto.response.*;
import com.example.booking_movie_ticket.entity.Schedule;

import java.time.Instant;
import java.util.List;

public interface ScheduleService {
    void updateSchedule(Long scheduleId, ScheduleRequest request);

    //    List<ScheduleListResponse> getAllSchedules();
    List<Schedule> getAllSchedules();

    ScheduleCreateResponse createSchedule(ScheduleRequest request);

    List<RoomListResponse> getAvailableRooms(Instant startTime, Instant endTime);

    List<ScheduleByMovieResponse> getSchedulesByMovieId(Long movieId);

}
