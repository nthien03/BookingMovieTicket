package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.SeatTypeRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatTypeResponse;
import com.example.booking_movie_ticket.entity.SeatType;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.SeatTypeRepository;
import com.example.booking_movie_ticket.service.SeatTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SeatTypeServiceImpl implements SeatTypeService {

    private final SeatTypeRepository seatTypeRepository;

    public SeatTypeServiceImpl(SeatTypeRepository seatTypeRepository) {
        this.seatTypeRepository = seatTypeRepository;
    }

    @Override
    public long createSeatType(SeatTypeRequest request) {
        if (seatTypeRepository.existsBySeatTypeNameIgnoreCase(request.getSeatTypeName().trim())) {
            throw new AppException(ErrorCode.SEAT_TYPE_EXISTED);
        }

        SeatType seatType = SeatType.builder()
                .seatTypeName(request.getSeatTypeName().trim())
                .price(request.getPrice())
                .status(request.getStatus())
                .build();

        return seatTypeRepository.save(seatType).getId();
    }

    @Override
    public PageResponse getAllSeatTypes(Specification<SeatType> spec, Pageable pageable) {
        Page<SeatType> page = seatTypeRepository.findAll(spec, pageable);
        List<SeatTypeResponse> result = page.getContent().stream()
                .map(seatType -> SeatTypeResponse.builder()
                        .id(seatType.getId())
                        .seatTypeName(seatType.getSeatTypeName())
                        .price(seatType.getPrice())
                        .status(seatType.getStatus())
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
    public SeatTypeResponse getSeatType(long id) {
        SeatType seatType = seatTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_TYPE_NOT_EXISTED));

        return SeatTypeResponse.builder()
                .id(seatType.getId())
                .seatTypeName(seatType.getSeatTypeName())
                .price(seatType.getPrice())
                .status(seatType.getStatus())
                .build();
    }

    @Override
    public void updateSeatType(long id, SeatTypeRequest request) {
        SeatType seatType = seatTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_TYPE_NOT_EXISTED));

        Optional<SeatType> existing = seatTypeRepository.findBySeatTypeNameIgnoreCase(request.getSeatTypeName().trim());
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new AppException(ErrorCode.SEAT_TYPE_EXISTED);
        }

        seatType.setSeatTypeName(request.getSeatTypeName().trim());
        seatType.setPrice(request.getPrice());
        seatType.setStatus(request.getStatus());

        seatTypeRepository.save(seatType);
        log.info("Seat type updated successfully, id={}", id);
    }
}

