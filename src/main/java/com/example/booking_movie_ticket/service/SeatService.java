package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.SeatRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatByRoomResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatDetailResponse;
import com.example.booking_movie_ticket.entity.Seat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SeatService {
    long createSeat(SeatRequest request);
    PageResponse getAllSeats(Specification<Seat> spec, Pageable pageable);
    SeatDetailResponse getSeat(long id);
    void updateSeat(long id, SeatRequest request);
    List<SeatByRoomResponse> getSeatsByRoom(Long roomId);
}

