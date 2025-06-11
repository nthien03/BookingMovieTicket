package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.RoomRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.room.RoomResponse;
import com.example.booking_movie_ticket.entity.Room;
import com.example.booking_movie_ticket.service.RoomService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createRoom(@Valid @RequestBody RoomRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<Long>builder()
                        .code(1000)
                        .message("Room created successfully")
                        .data(roomService.createRoom(request))
                        .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAllRooms(
            @Filter Specification<Room> spec,
            Pageable pageable) {

        return ResponseEntity.ok().body(
                ApiResponse.<PageResponse>builder()
                        .code(1000)
                        .data(roomService.getAllRooms(spec, pageable))
                        .build());
    }
    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse<RoomResponse>> getRoom(
            @PathVariable long roomId) {

        return ResponseEntity.ok().body(
                ApiResponse.<RoomResponse>builder()
                        .code(1000)
                        .data(roomService.getRoom(roomId))
                        .build());
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponse<Void>> updateRoom(
            @PathVariable long roomId,
            @Valid @RequestBody RoomRequest request) {

        this.roomService.updateRoom(roomId, request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(1000)
                .message("Room updated successfully")
                .build());
    }


}
