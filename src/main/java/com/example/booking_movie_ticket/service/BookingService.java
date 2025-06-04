package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.BookingRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.booking.BookingDetailResponse;
import com.example.booking_movie_ticket.dto.response.booking.BookingResponse;
import com.example.booking_movie_ticket.entity.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BookingService {
    Long createBooking(BookingRequest request);
    BookingDetailResponse getBookingById(Long id);
    List<BookingResponse> getBookingsByUser(Long userId);
    PageResponse getAllBookings(Specification<Booking> spec, Pageable pageable);

    PageResponse getBookingHistoryByUser(Long userId, String movieName, Pageable pageable);
}

