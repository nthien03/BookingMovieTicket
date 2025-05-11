package com.example.booking_movie_ticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorCreateResponse {
    private Long id;
    private String fullName;
}
