package com.example.booking_movie_ticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "seattypes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String seatTypeName;

    @NotNull
    private Integer price;

    private Boolean status;

    @OneToMany(mappedBy = "seatType", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Seat> seats;

}
