package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.SeatRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatByRoomResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatDetailResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatStatusResponse;
import com.example.booking_movie_ticket.entity.Room;
import com.example.booking_movie_ticket.entity.Seat;
import com.example.booking_movie_ticket.entity.SeatType;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.RoomRepository;
import com.example.booking_movie_ticket.repository.SeatRepository;
import com.example.booking_movie_ticket.repository.SeatTypeRepository;
import com.example.booking_movie_ticket.service.SeatService;
import com.example.booking_movie_ticket.util.constant.BookingStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatTypeRepository seatTypeRepository;
    private final RoomRepository roomRepository;

    public SeatServiceImpl(SeatRepository seatRepository,
                           SeatTypeRepository seatTypeRepository,
                           RoomRepository roomRepository) {
        this.seatRepository = seatRepository;
        this.seatTypeRepository = seatTypeRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public long createSeat(SeatRequest request) {
        if (seatRepository.existsBySeatRowIgnoreCaseAndSeatNumberAndRoom_Id(
                request.getSeatRow().trim(), request.getSeatNumber(), request.getRoomId())) {
            throw new AppException(ErrorCode.SEAT_EXISTED);
        }

        SeatType seatType = seatTypeRepository.findById(request.getSeatTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_TYPE_NOT_EXISTED));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        Seat seat = Seat.builder()
                .seatRow(request.getSeatRow().trim())
                .seatNumber(request.getSeatNumber())
                .price(request.getPrice())
                .seatType(seatType)
                .room(room)
                .status(true)
                .build();

        return seatRepository.save(seat).getId();
    }

    @Override
    public PageResponse getAllSeats(Specification<Seat> spec, Pageable pageable) {
        Page<Seat> page = seatRepository.findAll(spec, pageable);
        List<SeatResponse> result = page.getContent().stream()
                .map(seat -> SeatResponse.builder()
                        .id(seat.getId())
                        .seatRow(seat.getSeatRow())
                        .seatNumber(seat.getSeatNumber())
                        .price(seat.getPrice())
                        .status(seat.getStatus())
                        .build())
                .toList();

        MetaPage meta = new MetaPage();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());

        return PageResponse.builder()
                .meta(meta)
                .result(result)
                .build();
    }

    @Override
    public SeatDetailResponse getSeat(long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));

        return SeatDetailResponse.builder()
                .id(seat.getId())
                .seatRow(seat.getSeatRow())
                .seatNumber(seat.getSeatNumber())
                .price(seat.getPrice())
                .status(seat.getStatus())
                .seatType(new SeatDetailResponse.SeatTypeInfo(
                        seat.getSeatType().getId(),
                        seat.getSeatType().getSeatTypeName()
                ))
                .room(new SeatDetailResponse.RoomInfo(
                        seat.getRoom().getId(),
                        seat.getRoom().getRoomName() // giả sử Room có getRoomName()
                ))
                .build();
    }


    @Override
    public void updateSeat(long id, SeatRequest request) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));

        boolean exists = seatRepository
                .existsBySeatRowIgnoreCaseAndSeatNumberAndRoom_Id(
                        request.getSeatRow().trim(), request.getSeatNumber(), request.getRoomId());
        if (exists && !(seat.getSeatRow().equalsIgnoreCase(request.getSeatRow()) &&
                seat.getSeatNumber().equals(request.getSeatNumber()) &&
                seat.getRoom().getId().equals(request.getRoomId()))) {
            throw new AppException(ErrorCode.SEAT_EXISTED);
        }

        SeatType seatType = seatTypeRepository.findById(request.getSeatTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_TYPE_NOT_EXISTED));

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_EXISTED));

        seat.setSeatRow(request.getSeatRow().trim());
        seat.setSeatNumber(request.getSeatNumber());
        seat.setPrice(request.getPrice());
        seat.setSeatType(seatType);
        seat.setRoom(room);
        seat.setStatus(request.getStatus());

        seatRepository.save(seat);

        log.info("Seat updated successfully, id={}", id);
    }

    @Override
    public List<SeatByRoomResponse> getSeatsByRoom(Long roomId) {
        List<Seat> seats = seatRepository.findByRoomId(roomId);

        return seats.stream().map(seat -> SeatByRoomResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .seatRow(seat.getSeatRow())
                .price(seat.getPrice())
                .status(seat.getStatus())
                .seatType(SeatByRoomResponse.SeatTypeInSeat.builder()
                        .id(seat.getSeatType().getId())
                        .seatTypeName(seat.getSeatType().getSeatTypeName())
                        .build())
                .build()
        ).toList();
    }

    @Override
    public List<SeatStatusResponse> getSeatStatuses(Long roomId, Long scheduleId) {
        List<Integer> holdStatusValues = List.of(
                BookingStatus.BOOKED.getValue(),
                BookingStatus.PAYMENT_PROCESSING.getValue(),
                BookingStatus.COMPLETED.getValue()
        );
        return seatRepository.findSeatStatusesByRoomAndSchedule(roomId, scheduleId, holdStatusValues);
    }

    @Override
    public List<Long> getBookedSeatsFromList(List<Long> seatIds, Long scheduleId) {
        List<Integer> holdStatusValues = List.of(
                BookingStatus.BOOKED.getValue(),
                BookingStatus.PAYMENT_PROCESSING.getValue(),
                BookingStatus.COMPLETED.getValue()
        );
        return seatRepository.getBookedSeatsFromList(seatIds,scheduleId, holdStatusValues);
    }
}

