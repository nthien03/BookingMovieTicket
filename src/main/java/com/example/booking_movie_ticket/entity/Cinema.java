package com.example.booking_movie_ticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cinemas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cinema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cinemaName;
    private String cinemaAddress;
    private Boolean status;

    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Room> rooms;
}
