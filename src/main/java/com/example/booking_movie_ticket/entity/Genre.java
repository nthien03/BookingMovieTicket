package com.example.booking_movie_ticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


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

    @NotNull
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "genres")
    @JsonIgnore
    private List<Movie> movies;

    private Boolean status;

}
