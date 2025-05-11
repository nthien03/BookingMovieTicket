package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GenreRequest {
    @NotBlank(message = "GENRE_NAME_BLANK")
    private String name;
    private String description;

}
