package com.example.booking_movie_ticket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String movieName;

    private String director;

    private String actors;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    private String poster;

    private String trailerUrl;

    private Integer duration;

    private String genre;

    private Instant releaseDate;

    private Integer ageRestriction;

    private Boolean status;

    private Instant createdAt;

    private  Instant updatedAt;

}
