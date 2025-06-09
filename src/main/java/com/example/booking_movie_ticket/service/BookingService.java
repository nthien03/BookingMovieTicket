package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.BookingRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.booking.BookingCreateResponse;
import com.example.booking_movie_ticket.dto.response.booking.BookingDetailResponse;
import com.example.booking_movie_ticket.dto.response.booking.BookingResponse;
import com.example.booking_movie_ticket.entity.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface BookingService {
    BookingCreateResponse createBooking(BookingRequest request);
    BookingDetailResponse getBookingById(Long id);
    List<BookingResponse> getBookingsByUser(Long userId);
    PageResponse getAllBookings(Specification<Booking> spec, Pageable pageable);
    void updateBookingStatus(String bookingCode, Integer status);

    PageResponse getBookingHistoryByUser(Long userId, String movieName, Pageable pageable);
    boolean isSeatAvailable(Long seatId, Long scheduleId);
    Map<Long, Boolean> checkSeatsAvailability(List<Long> seatIds, Long scheduleId);
    List<Long> getBookedSeats(Long scheduleId);

}

