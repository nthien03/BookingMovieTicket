package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.request.ScheduleRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.MovieCreateResponse;
import com.example.booking_movie_ticket.dto.response.ScheduleCreateResponse;
import com.example.booking_movie_ticket.entity.Schedule;
import com.example.booking_movie_ticket.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleCreateResponse>> createSchedule(@Valid @RequestBody ScheduleRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<ScheduleCreateResponse>builder()
                        .code(1000)
                        .message("Schedule created successfully")
                        .data(scheduleService.createSchedule(request)).build()
                );
    }
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }
}
