package com.example.booking_movie_ticket.entity;

import com.example.booking_movie_ticket.util.SecurityUtil;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String nationality;
    private Boolean status;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void handleBeforeCreate(){
        this.createdBy = SecurityUtil.getCurrentUserLogin().orElse("");
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate(){
        this.updatedBy = SecurityUtil.getCurrentUserLogin().orElse("");
        this.updatedAt = Instant.now();
    }

}
