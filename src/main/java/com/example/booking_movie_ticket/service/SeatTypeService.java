package com.example.booking_movie_ticket.service;


import com.example.booking_movie_ticket.dto.request.SeatTypeRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.seat.SeatTypeResponse;
import com.example.booking_movie_ticket.entity.SeatType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SeatTypeService {
    long createSeatType(SeatTypeRequest request);
    PageResponse getAllSeatTypes(Specification<SeatType> spec,
                                 Pageable pageable);
    SeatTypeResponse getSeatType(long id);
    void updateSeatType(long id, SeatTypeRequest request);
}

