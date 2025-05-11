package com.example.booking_movie_ticket.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private Boolean status;

}
