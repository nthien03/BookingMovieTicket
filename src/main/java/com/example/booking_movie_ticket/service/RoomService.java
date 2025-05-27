package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.RoomRequest;
import com.example.booking_movie_ticket.dto.response.room.RoomResponse;

import java.util.List;

public interface RoomService {
    long createRoom(RoomRequest request);
    //PageResponse getAllRooms(Specification<Room> spec, Pageable pageable);
    RoomResponse getRoom(long roomId);
    void updateRoom(long roomId, RoomRequest request);
    List<RoomResponse> getAllRooms();
}
