package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.BookingRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.booking.*;
import com.example.booking_movie_ticket.entity.Booking;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.BookingRepository;
import com.example.booking_movie_ticket.repository.UserRepository;
import com.example.booking_movie_ticket.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Long createBooking(BookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Booking booking = Booking.builder()
                .bookingCode(request.getBookingCode())
                .amount(request.getAmount())
                .totalPrice(request.getTotalPrice())
                .bookingDate(Instant.now())
                .status(request.getStatus())
                .user(user)
                .build();

        return bookingRepository.save(booking).getId();
    }

    @Override
    public BookingDetailResponse getBookingById(Long id) {

        Booking b = bookingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        List<TicketResponse> ticketResponses = b.getTickets().stream()
                .map(t -> TicketResponse.builder()
                        .id(t.getId())
                        .price(t.getPrice())
                        .ticketCode(t.getTicketCode())
                        .createdAt(t.getCreatedAt())
                        .status(t.getStatus())
                        .scheduleId(t.getSchedule().getId())
                        .seatId(t.getSeat().getId())
                        .bookingId(b.getId())
                        .build())
                .toList();

        PaymentResponse paymentResponse = Optional.ofNullable(b.getPayment())
                .map(p -> PaymentResponse.builder()
                        .paymentId(p.getPaymentId())
                        .method(p.getMethod())
                        .paymentCode(p.getPaymentCode())
                        .paymentDate(p.getPaymentDate())
                        .amount(p.getAmount())
                        .status(p.getStatus())
                        .bookingId(b.getId())
                        .build())
                .orElse(null);

        return BookingDetailResponse.builder()
                .id(b.getId())
                .bookingCode(b.getBookingCode())
                .amount(b.getAmount())
                .totalPrice(b.getTotalPrice())
                .bookingDate(b.getBookingDate())
                .status(b.getStatus())
                .userId(b.getUser().getId())
                .tickets(ticketResponses)
                .payment(paymentResponse)
                .build();
    }


    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream().map(this::toResponse).toList();
    }

//    @Override
//    public void updateBookingStatus(Long id, Boolean status) {
//        Booking b = bookingRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_FOUND));
//        b.setStatus(status);
//        bookingRepository.save(b);
//    }

    @Override
    public PageResponse getAllBookings(Specification<Booking> spec, Pageable pageable) {
        Page<Booking> bookingPage = bookingRepository.findAll(spec, pageable);

        List<BookingResponse> bookingResponses = bookingPage.getContent().stream()
                .map(b -> BookingResponse.builder()
                        .id(b.getId())
                        .bookingCode(b.getBookingCode())
                        .amount(b.getAmount())
                        .totalPrice(b.getTotalPrice())
                        .bookingDate(b.getBookingDate())
                        .status(b.getStatus())
                        .user(new BookingResponse.UserInBooking(b.getUser().getId(), b.getUser().getUsername()))
                        .build())
                .toList();

        MetaPage meta = MetaPage.builder()
                .page(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .pages(bookingPage.getTotalPages())
                .total(bookingPage.getTotalElements())
                .build();

        return PageResponse.builder()
                .meta(meta)
                .result(bookingResponses)
                .build();
    }

    @Override
    public PageResponse getBookingHistoryByUser(Long userId, String movieName, Pageable pageable) {
        Page<Booking> bookingsPage;

        if (movieName != null && !movieName.trim().isEmpty()) {
            // Tìm kiếm theo tên phim
            bookingsPage = bookingRepository
                    .findByUserIdAndTicketsScheduleMovieMovieNameContainingIgnoreCaseOrderByBookingDateDesc(
                            userId, movieName.trim(), pageable);
        } else {
            // Lấy tất cả
            bookingsPage = bookingRepository.findByUserIdOrderByBookingDateDesc(userId, pageable);
        }

        List<BookingHistoryListResponse> bookingsList = bookingsPage.getContent().stream()
                .map(booking -> {
                    String movieNameExtracted = booking.getTickets().isEmpty() ? null :
                            booking.getTickets().get(0).getSchedule().getMovie().getMovieName();

                    return BookingHistoryListResponse.builder()
                            .id(booking.getId())
                            .bookingCode(booking.getBookingCode())
                            .bookingDate(booking.getBookingDate())
                            .movieName(movieNameExtracted)
                            .totalPrice(booking.getTotalPrice())
                            .build();
                }).toList();


        MetaPage meta = MetaPage.builder()
                .page(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .pages(bookingsPage.getTotalPages())
                .total(bookingsPage.getTotalElements())
                .build();

        return PageResponse.builder()
                .meta(meta)
                .result(bookingsList)
                .build();
    }


    private BookingResponse toResponse(Booking b) {
        return BookingResponse.builder()
                .id(b.getId())
                .bookingCode(b.getBookingCode())
                .amount(b.getAmount())
                .totalPrice(b.getTotalPrice())
                .bookingDate(b.getBookingDate())
                .status(b.getStatus())
                .user(new BookingResponse.UserInBooking(b.getUser().getId(), b.getUser().getUsername()))
                .build();
    }


}

