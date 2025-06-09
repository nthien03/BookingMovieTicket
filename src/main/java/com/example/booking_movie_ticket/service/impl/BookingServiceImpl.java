package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.BookingRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.booking.*;
import com.example.booking_movie_ticket.dto.response.schedule.ScheduleByMovieResponse;
import com.example.booking_movie_ticket.entity.*;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.BookingRepository;
import com.example.booking_movie_ticket.repository.SeatRepository;
import com.example.booking_movie_ticket.repository.UserRepository;
import com.example.booking_movie_ticket.service.BookingService;
import com.example.booking_movie_ticket.util.constant.BookingStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
    }

    public static String generateBookingCode() {
        Random random = new Random();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        int randomSuffix = 100 + random.nextInt(900); // 100-999
        String code = timestamp + randomSuffix;
        return code;
    }

    private String generateUniqueBookingCode() {
        String bookingCode;
        int attempts = 0;
        int maxAttempts = 50;
        do {
            bookingCode = generateBookingCode();
            attempts++;
            if (attempts > maxAttempts) {
                throw new AppException(ErrorCode.BOOKING_CODE_FAILED);
            }
        } while (bookingRepository.existsByBookingCode(bookingCode));
        return bookingCode;
    }


    @Override
    public BookingCreateResponse createBooking(BookingRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Booking booking = Booking.builder()
                .bookingCode(generateUniqueBookingCode())
                .amount(request.getAmount())
                .totalPrice(request.getTotalPrice())
                .bookingDate(Instant.now())
                .paymentMethod(request.getPaymentMethod())
                .status(BookingStatus.PAYMENT_PROCESSING.getValue())
                .user(user)
                .build();

        Booking saved = bookingRepository.save(booking);

        return BookingCreateResponse.builder()
                .id(saved.getId())
                .bookingCode(saved.getBookingCode())
                .build();
    }

    @Override
    public BookingDetailResponse getBookingById(Long id) {

        Booking b = bookingRepository.findBookingWithDetails(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        if (b.getTickets().isEmpty()) {
            throw new AppException(ErrorCode.BOOKING_HAS_NO_TICKET);
        }

        Ticket firstTicket = b.getTickets().get(0);
        Schedule schedule = firstTicket.getSchedule();
        Movie movie = schedule.getMovie();
        Room room = schedule.getRoom();

        List<TicketInBookingResponse> ticketResponses = b.getTickets().stream()
                .map(t -> {
                    Seat seat = t.getSeat();
                    return new TicketInBookingResponse(
                            t.getId(),
                            t.getPrice(),
                            new TicketInBookingResponse.SeatInBooking(
                                    seat.getId(),
                                    seat.getSeatRow(),
                                    seat.getSeatNumber(),
                                    seat.getPrice()
                            )
                    );
                })
                .toList();

        // Map user
        User user = b.getUser();
        BookingDetailResponse.UserInfoInBooking userDTO = new BookingDetailResponse.UserInfoInBooking(
                user.getId(),
                user.getUsername(),
                user.getFullName()
        );

        return BookingDetailResponse.builder()
                .id(b.getId())
                .bookingCode(b.getBookingCode())
                .amount(b.getAmount())
                .totalPrice(b.getTotalPrice())
                .bookingDate(b.getBookingDate())
                .status(b.getStatus())
                .movieName(movie.getMovieName())
                .roomName(room.getRoomName())
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .user(userDTO)
                .tickets(ticketResponses)
                .paymentMethod(b.getPaymentMethod())
                .build();
    }


    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public void updateBookingStatus(String bookingCode, Integer status) {
//        Booking booking = bookingRepository.findByBookingCode(bookingCode).orElseThrow(
//                () -> new AppException(ErrorCode.BOOKING_NOT_EXISTED)
//        );
//        booking.setStatus(status);
//        bookingRepository.save(booking);

        Booking booking = bookingRepository.findByBookingCode(bookingCode).orElse(null);
        if (booking != null) {
            booking.setStatus(status);
            bookingRepository.save(booking);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void expireOldPendingOrders() {
        Instant expire = Instant.now().minusSeconds(6 * 60);
        int updated = bookingRepository.updateStatusForExpiredBookings(
                BookingStatus.PAYMENT_FAILED.getValue(),
                BookingStatus.PAYMENT_PROCESSING.getValue(),
                expire
        );

        System.out.println("Canceled expired bookings: " + updated);
    }


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
                            .status(booking.getStatus())
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

    // Kiểm tra 1 ghế có thể đặt không
    @Override
    public boolean isSeatAvailable(Long seatId, Long scheduleId) {
//        log.info("Checking seat availability - seatId: {}, scheduleId: {}", seatId, scheduleId);
//        // Kiểm tra ghế có tồn tại và active không
//        Optional<Seat> seatOpt = seatRepository.findById(seatId);
//        if (seatOpt.isEmpty() || !seatOpt.get().getStatus()) {
//            throw new AppException(ErrorCode.SEAT_INVALID);
//        }
//
//        // Kiểm tra ghế đã được đặt chưa
//        boolean isBooked = bookingRepository.isSeatBookedForSchedule(seatId, scheduleId);
//        return !isBooked;
        return false;
    }

    // Kiểm tra nhiều ghế cùng lúc
    @Override
    public Map<Long, Boolean> checkSeatsAvailability(List<Long> seatIds, Long scheduleId) {
//        log.info("Checking multiple seats availability - seatIds: {}, scheduleId: {}", seatIds, scheduleId);
//
//        // Lấy ghế active
//        List<Seat> activeSeats = seatRepository.findByIdInAndStatusTrue(seatIds);
//        Set<Long> activeSeatIds = activeSeats.stream()
//                .map(Seat::getId)
//                .collect(Collectors.toSet());
//
//        // Lấy ghế đã đặt
//        List<Long> bookedSeatIds = bookingRepository.getBookedSeatsFromList(seatIds, scheduleId);
//        Set<Long> bookedSeatSet = new HashSet<>(bookedSeatIds);
//
//
//        Map<Long, Boolean> result = new HashMap<>();
//        for (Long seatId : seatIds) {
//            boolean available = activeSeatIds.contains(seatId) && !bookedSeatSet.contains(seatId);
//            result.put(seatId, available);
//        }
        // return result;
        return null;
    }


    @Override
    public List<Long> getBookedSeats(Long scheduleId) {
        return List.of();
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

