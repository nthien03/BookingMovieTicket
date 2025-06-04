package com.example.booking_movie_ticket.dto.response.seat;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatTypeResponse {
    private Long id;
    private String seatTypeName;
    private Integer price;
    private Boolean status;
}

