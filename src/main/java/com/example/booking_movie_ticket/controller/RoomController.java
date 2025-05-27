package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.RoomRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.room.RoomResponse;
import com.example.booking_movie_ticket.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }
//    @GetMapping
//    public ResponseEntity<ApiResponse<PageResponse>> getAllGenres(
//            @Filter Specification<Genre> spec,
//            Pageable pageable) {
//
//
//        return ResponseEntity.ok().body(
//                ApiResponse.<PageResponse>builder()
//                        .code(1000)
//                        .data(genreService.getAllGenres(spec, pageable))
//                        .build());
//    }
//
//    @GetMapping("/{genreId}")
//    public ResponseEntity<ApiResponse<GenreResponse>> getGenre(@PathVariable long genreId) {
//
//        return ResponseEntity.ok().body(
//                ApiResponse.<GenreResponse>builder()
//                        .code(1000)
//                        .data(genreService.getGenre(genreId))
//                        .build());
//    }
//
//    @PutMapping("/{genreId}")
//    public ResponseEntity<ApiResponse<Void>> updateGenre(@PathVariable long genreId, @Valid @RequestBody GenreRequest request) {
//        this.genreService.updateGenre(genreId, request);
//
//        return ResponseEntity.ok(ApiResponse.<Void>builder()
//                .code(1000)
//                .message("Genre updated successfully")
//                .build());
//    }


}
