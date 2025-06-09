package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.SeatRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatByRoomResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatDetailResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatStatusResponse;
import com.example.booking_movie_ticket.entity.Seat;
import com.example.booking_movie_ticket.service.SeatService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createSeat(@Valid @RequestBody SeatRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<Long>builder()
                        .code(1000)
                        .message("Seat created successfully")
                        .data(seatService.createSeat(request))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAllSeats(
            @Filter Specification<Seat> spec,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.<PageResponse>builder()
                .code(1000)
                .data(seatService.getAllSeats(spec, pageable))
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SeatDetailResponse>> getSeat(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.<SeatDetailResponse>builder()
                .code(1000)
                .data(seatService.getSeat(id))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateSeat(@PathVariable long id,
                                                        @Valid @RequestBody SeatRequest request) {
        seatService.updateSeat(id, request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(1000)
                .message("Seat updated successfully")
                .build());
    }

    @GetMapping("/generate-seat-sql")
    public ResponseEntity<String> generateSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO seats (seat_row, seat_number, price, status, seat_type_id, room_id)\nVALUES\n");

        char[] rows = {'A','B','C','D','E','F','G','H','I','J'};
        for (char row : rows) {
            int seatTypeId = (row == 'A' || row == 'B' || row == 'J') ? 2 : 1;
            int price = seatTypeId == 2 ? 80000 : 120000;

            for (int col = 1; col <= 15; col++) {
                sb.append(String.format("('%c', %d, %d, true, %d, 1),\n", row, col, price, seatTypeId));
            }
        }

        return ResponseEntity.ok(sb.toString());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ApiResponse<List<SeatByRoomResponse>>> getSeatsByRoom(@PathVariable Long roomId) {
        List<SeatByRoomResponse> seats = seatService.getSeatsByRoom(roomId);
        return ResponseEntity.ok(
                ApiResponse.<List<SeatByRoomResponse>>builder()
                        .code(1000)
                        .data(seats)
                        .build()
        );
    }

    @GetMapping("/availability")
    public ResponseEntity<ApiResponse<List<SeatStatusResponse>>> getSeatStatuses(
            @RequestParam Long roomId,
            @RequestParam Long scheduleId
    ) {

        return ResponseEntity.ok(
                ApiResponse.<List<SeatStatusResponse>>builder()
                        .code(1000)
                        .data(seatService.getSeatStatuses(roomId, scheduleId))
                        .build());
    }

}

