package com.example.booking_movie_ticket.dto.response.actor;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActorResponse {
    private Long id;
    private String fullName;
    private LocalDate dateOfBirth;
    private String nationality;
    private Boolean status;
}
