package com.example.booking_movie_ticket.service.impl;


import com.example.booking_movie_ticket.dto.request.RoomRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.RoomResponse;
import com.example.booking_movie_ticket.entity.Room;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.CinemaRepository;
import com.example.booking_movie_ticket.repository.RoomRepository;
import com.example.booking_movie_ticket.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
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


//    @Override
//    public long createGenre(GenreRequest request) {
//
//
//    }
//
//    @Override
//    public PageResponse getAllGenres(Specification<Genre> spec, Pageable pageable) {
//        Page<Genre> genrePage = this.genreRepository.findAll(spec, pageable);
//        List<GenreResponse> genreList = genrePage.getContent()
//                .stream()
//                .map(genre -> GenreResponse.builder()
//                        .id(genre.getId())
//                        .name(genre.getName())
//                        .description(genre.getDescription())
//                        .status(genre.getStatus())
//                        .build()
//                ).toList();
//
//        MetaPage metaPage = new MetaPage();
//        metaPage.setPage(pageable.getPageNumber() + 1);
//        metaPage.setPageSize(pageable.getPageSize());
//        metaPage.setPages(genrePage.getTotalPages());
//        metaPage.setTotal(genrePage.getTotalElements());
//
//        return PageResponse.builder()
//                .meta(metaPage)
//                .result(genreList)
//                .build();
//    }
//
//
//    @Override
//    public GenreResponse getGenre(long genreId) {
//        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new AppException(ErrorCode.GENRE_NOT_EXISTED));
//        return GenreResponse.builder()
//                .id(genre.getId())
//                .name(genre.getName())
//                .description(genre.getDescription())
//                .status(genre.getStatus())
//                .build();
//    }
//
//    @Override
//    public void updateGenre(long genreId, GenreRequest request) {
//        if (genreRepository.existsByName(request.getName())) {
//            throw new AppException(ErrorCode.GENRE_EXISTED);
//        }
//        Genre genre = this.genreRepository.findById(genreId).orElseThrow(() -> new AppException(ErrorCode.GENRE_NOT_EXISTED));
//        genre.setName(request.getName());
//        genre.setDescription(request.getDescription());
//        genre.setStatus(request.getStatus());
//        this.genreRepository.save(genre);
//
//        log.info("Genre has updated successfully, genreId={}", genre.getId());
//    }


    @Override
    public long createRoom(RoomRequest request) {
        if (roomRepository.existsByRoomName(request.getRoomName())) {
            throw new AppException(ErrorCode.ROOM_EXISTED);
        }
        Room room = Room.builder()
                .roomName(request.getRoomName())
                .cinema(cinemaRepository.findById(1L).orElse(null))
                .status(request.getStatus())
                .total_row(request.getTotal_row())
                .total_column(request.getTotal_column())
                .build();
        Room saved = roomRepository.save(room);
        return saved.getId();
    }

    @Override
    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(room -> RoomResponse.builder()
                        .id(room.getId())
                        .roomName(room.getRoomName())
                        .status(room.getStatus())
                        .total_row(room.getTotal_row())
                        .total_column(room.getTotal_column())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponse getRoom(long roomId) {
        return null;
    }

    @Override
    public void updateRoom(long roomId, RoomRequest request) {

    }
}
