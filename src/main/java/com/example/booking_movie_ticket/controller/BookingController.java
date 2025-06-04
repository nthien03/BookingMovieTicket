package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.BookingRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.booking.BookingDetailResponse;
import com.example.booking_movie_ticket.dto.response.booking.BookingResponse;
import com.example.booking_movie_ticket.entity.Booking;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.service.BookingService;
import com.example.booking_movie_ticket.service.UserService;
import com.example.booking_movie_ticket.util.SecurityUtil;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<Long>builder()
                        .code(1000)
                        .message("Created")
                        .data(bookingService.createBooking(request)).build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingDetailResponse>> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<BookingDetailResponse>builder()
                        .code(1000)
                        .data(bookingService.getBookingById(id))
                        .build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<BookingResponse>>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
                ApiResponse.<List<BookingResponse>>builder()
                        .code(1000)
                        .data(bookingService.getBookingsByUser(userId)
                        ).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAllBookings(
            @Filter Specification<Booking> spec,
            Pageable pageable) {

        return ResponseEntity.ok(ApiResponse.<PageResponse>builder()
                .code(1000)
                .data(bookingService.getAllBookings(spec, pageable))
                .build());
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<PageResponse>> getBookingHistoryByUser(
            @RequestParam(value = "movieName", required = false) String movieName,
            Pageable pageable
    ){
        String username = SecurityUtil.getCurrentUserLogin().orElseThrow(
                () -> new AppException(ErrorCode.INVALID_TOKEN_ERROR)
        );

        User user = userService.getUserByUsername(username);

        Long userId = user.getId();

        return ResponseEntity.ok(ApiResponse.<PageResponse>builder()
                .code(1000)
                .data(bookingService.getBookingHistoryByUser(userId,movieName,pageable))
                .build());
    }


//    @PutMapping("/{id}/status")
//    public ResponseEntity<ApiResponse<Void>> updateStatus(@PathVariable Long id, @RequestParam Boolean status) {
//        bookingService.updateBookingStatus(id, status);
//        return ResponseEntity.ok(ApiResponse.<Void>builder().code(1000).message("Status updated").build());
//    }
}

