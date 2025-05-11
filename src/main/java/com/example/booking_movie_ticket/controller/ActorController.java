package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.ActorRequest;
import com.example.booking_movie_ticket.dto.response.ActorCreateResponse;
import com.example.booking_movie_ticket.dto.response.ActorResponse;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.service.ActorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ActorCreateResponse>> createActor(@Valid @RequestBody ActorRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<ActorCreateResponse>builder()
                        .code(1000)
                        .message("Actor created successfully")
                        .data(actorService.createActor(request))
                        .build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>>getAllActors(
            @RequestParam(value = "current", defaultValue = "1") int current,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(current- 1, pageSize);

        return ResponseEntity.ok().body(
                ApiResponse.<PageResponse>builder()
                        .code(1000)
                        .data(actorService.getAllActors(pageable))
                        .build());
    }

    @GetMapping("/{actorId}")
    public ResponseEntity<ApiResponse<ActorResponse>> getActor(@PathVariable long actorId) {
        return ResponseEntity.ok().body(
                ApiResponse.<ActorResponse>builder()
                        .code(1000)
                        .data(actorService.getActorById(actorId))
                        .build());
    }

    @PutMapping("/{actorId}")
    public ResponseEntity<ApiResponse<Void>> updateActor(@PathVariable long actorId, @Valid @RequestBody ActorRequest request) {
        this.actorService.updateActor(actorId, request);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(1000)
                .message("Actor updated successfully")
                .build());
    }
}
