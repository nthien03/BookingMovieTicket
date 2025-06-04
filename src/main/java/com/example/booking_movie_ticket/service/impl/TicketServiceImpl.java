package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.TicketRequest;
import com.example.booking_movie_ticket.dto.response.booking.TicketResponse;
import com.example.booking_movie_ticket.entity.Booking;
import com.example.booking_movie_ticket.entity.Schedule;
import com.example.booking_movie_ticket.entity.Seat;
import com.example.booking_movie_ticket.entity.Ticket;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.BookingRepository;
import com.example.booking_movie_ticket.repository.ScheduleRepository;
import com.example.booking_movie_ticket.repository.SeatRepository;
import com.example.booking_movie_ticket.repository.TicketRepository;
import com.example.booking_movie_ticket.service.TicketService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final ScheduleRepository scheduleRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, BookingRepository bookingRepository, SeatRepository seatRepository, ScheduleRepository scheduleRepository) {
        this.ticketRepository = ticketRepository;
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Long createTicket(TicketRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new AppException(ErrorCode.SEAT_NOT_EXISTED));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_EXISTED));

        Ticket ticket = Ticket.builder()
                .price(request.getPrice())
                .ticketCode(request.getTicketCode())
                .status(request.getStatus())
                .createdAt(Instant.now())
                .booking(booking)
                .seat(seat)
                .schedule(schedule)
                .build();

        return ticketRepository.save(ticket).getId();
    }

    @Override
    public List<TicketResponse> getTicketsByBooking(Long bookingId) {
        return ticketRepository.findByBookingId(bookingId)
                .stream().map(t -> TicketResponse.builder()
                        .id(t.getId())
                        .price(t.getPrice())
                        .ticketCode(t.getTicketCode())
                        .status(t.getStatus())
                        .seatId(t.getSeat().getId())
                        .scheduleId(t.getSchedule().getId())
                        .bookingId(t.getBooking().getId())
                        .createdAt(t.getCreatedAt())
                        .build()).toList();
    }


}

