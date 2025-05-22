package com.example.booking_movie_ticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.List;

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

    @NotNull
    private String movieName;

    @NotNull
    private String director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "movies" })
    @JoinTable(name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @NotNull
    private String poster;

    @NotNull
    private String trailerUrl;

    @NotNull
    private Integer duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "movies" })
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @NotNull
    private Instant releaseDate;

    private Integer ageRestriction;

    private Boolean status;

    private Instant createdAt;

    private Instant updatedAt;


}
