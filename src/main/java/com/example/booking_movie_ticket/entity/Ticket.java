package com.example.booking_movie_ticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long price;

    @NotNull
    private String ticketCode;

    @NotNull
    private Instant createdAt;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    // Quan hệ với Seat
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    // Quan hệ với Booking
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;
}

