package com.example.booking_movie_ticket.entity;

import com.example.booking_movie_ticket.util.constant.GenderEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;
    private String password;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    private String address;
    private Boolean status;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    private String refreshToken;




}
