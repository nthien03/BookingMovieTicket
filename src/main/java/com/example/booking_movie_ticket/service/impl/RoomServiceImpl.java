package com.example.booking_movie_ticket.service.impl;


import com.example.booking_movie_ticket.dto.request.RoomRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.room.RoomResponse;
import com.example.booking_movie_ticket.entity.Room;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.CinemaRepository;
import com.example.booking_movie_ticket.repository.RoomRepository;
import com.example.booking_movie_ticket.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final CinemaRepository cinemaRepository;

    public RoomServiceImpl(RoomRepository roomRepository, CinemaRepository cinemaRepository) {
        this.roomRepository = roomRepository;
        this.cinemaRepository = cinemaRepository;
    }


    @Override
    public long createRoom(RoomRequest request) {
        if (roomRepository.existsByRoomName(request.getRoomName())) {
            throw new AppException(ErrorCode.ROOM_EXISTED);
        }
        Room room = Room.builder()
                .roomName(request.getRoomName().trim())
                .cinema(cinemaRepository.findById(1L).orElse(null))
                .status(request.getStatus())
                .total_row(request.getTotal_row())
                .total_column(request.getTotal_column())
                .build();
        Room saved = roomRepository.save(room);
        return saved.getId();
    }

    @Override
    public PageResponse getAllRooms(Specification<Room> spec, Pageable pageable) {
        Page<Room> roomPage = this.roomRepository.findAll(spec, pageable);
        List<RoomResponse> roomList = roomPage.getContent()
                .stream()
                .map(room -> RoomResponse.builder()
                        .id(room.getId())
                        .roomName(room.getRoomName())
                        .status(room.getStatus())
                        .total_row(room.getTotal_row())
                        .total_column(room.getTotal_column())
                        .build()
                ).toList();

        MetaPage metaPage = new MetaPage();
        metaPage.setPage(pageable.getPageNumber() + 1);
        metaPage.setPageSize(pageable.getPageSize());
        metaPage.setPages(roomPage.getTotalPages());
        metaPage.setTotal(roomPage.getTotalElements());

        return PageResponse.builder()
                .meta(metaPage)
                .result(roomList)
                .build();
    }

//    @Override
//    public List<RoomResponse> getAllRooms() {
//        return roomRepository.findAll().stream()
//                .map(room -> RoomResponse.builder()
//                        .id(room.getId())
//                        .roomName(room.getRoomName())
//                        .status(room.getStatus())
//                        .total_row(room.getTotal_row())
//                        .total_column(room.getTotal_column())
//                        .build())
//                .collect(Collectors.toList());
//    }

    @Override
    public RoomResponse getRoom(long roomId) {
        Room room = this.roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        return RoomResponse.builder()
                .id(room.getId())
                .roomName(room.getRoomName())
                .status(room.getStatus())
                .total_row(room.getTotal_row())
                .total_column(room.getTotal_column())
                .build();
    }

    @Override
    public void updateRoom(long roomId, RoomRequest request) {
        // Check exist by id
        Room room = this.roomRepository.findById(roomId)
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        // Check if room name already exists
        Optional<Room> existing = roomRepository
                .findByRoomName(request.getRoomName().trim());

        if (existing.isPresent() && !existing.get().getId().equals(roomId)) {
            throw new AppException(ErrorCode.ROOM_EXISTED);
        }


        room.setRoomName(request.getRoomName().trim());
        room.setStatus(request.getStatus());
        room.setTotal_row(request.getTotal_row());
        room.setTotal_column(request.getTotal_column());

        this.roomRepository.save(room);
    }
}
