package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.request.RoomRequest;
import com.example.booking_movie_ticket.dto.request.ScheduleRequest;
import com.example.booking_movie_ticket.dto.response.*;
import com.example.booking_movie_ticket.dto.response.room.RoomListResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleByMovieResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleCreateResponse;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleListResponse;
import com.example.booking_movie_ticket.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
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
    public ResponseEntity<ApiResponse<PageResponse>> getAllSchedules(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant date,
            @RequestParam(required = false) String movieName,
            Pageable pageable
    ) {
        // Ép sort nếu FE không truyền sort

        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                    Sort.by(Sort.Direction.DESC, "startTime"));
        }
        return ResponseEntity.ok().body(
                ApiResponse.<PageResponse>builder()
                        .code(1000)
                        .data(scheduleService.getSchedules(date,movieName, pageable))
                        .build());
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomListResponse>> getAvailableRooms(
            @RequestParam Instant startTime,
            @RequestParam Instant endTime
    ) {
        List<RoomListResponse> availableRooms = scheduleService.getAvailableRooms(startTime, endTime);
        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ApiResponse<List<ScheduleByMovieResponse>>> getSchedulesByMovieId(@PathVariable Long movieId) {
        return  ResponseEntity.ok(
                ApiResponse.<List<ScheduleByMovieResponse>>builder()
                        .code(1000)
                        .data( scheduleService.getSchedulesByMovieId(movieId))
                        .build()
        );

    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<ScheduleListResponse>> getScheduleById(
            @PathVariable long scheduleId) {

        return ResponseEntity.ok().body(
                ApiResponse.<ScheduleListResponse>builder()
                        .code(1000)
                        .data(scheduleService.getScheduleById(scheduleId))
                        .build());
    }
    @PutMapping("/{scheduleId}")
    public ResponseEntity<ApiResponse<Void>> updateSchedule(
            @PathVariable long scheduleId,
            @Valid @RequestBody ScheduleRequest request) {

        this.scheduleService.updateSchedule(scheduleId, request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(1000)
                .message("Schedule updated successfully")
                .build());
    }
}
