package com.example.booking_movie_ticket.dto.response.genre;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreResponse {
    private Long id;

    private String name;

    private String description;

    private Boolean status;
}
