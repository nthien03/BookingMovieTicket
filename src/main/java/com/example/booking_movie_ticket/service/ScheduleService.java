package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.ScheduleRequest;
import com.example.booking_movie_ticket.dto.response.MovieListResponse;
import com.example.booking_movie_ticket.dto.response.ScheduleCreateResponse;
import com.example.booking_movie_ticket.dto.response.ScheduleListResponse;
import com.example.booking_movie_ticket.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    void updateSchedule(Long scheduleId, ScheduleRequest request);

    //    List<ScheduleListResponse> getAllSchedules();
    List<Schedule> getAllSchedules();

    ScheduleCreateResponse createSchedule(ScheduleRequest request);
}
