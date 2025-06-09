//package com.example.booking_movie_ticket.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//import java.time.Instant;
//
//@Entity
//@Table(name = "payments")
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Payment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long paymentId;
//
//    @NotNull
//    private String method;
//
//    @NotNull
//    private String paymentCode;
//
//    @NotNull
//    private Instant paymentDate;
//
//    @NotNull
//    private Long amount;
//
//    private Boolean status;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "booking_id", unique = true)
//    private Booking booking;
//}
//
