package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ActorRequest {
    @NotBlank(message = "ACTOR_NAME_BLANK")
    private String fullName;
    private LocalDate dateOfBirth;
    private String nationality;
    private Boolean status;
}
