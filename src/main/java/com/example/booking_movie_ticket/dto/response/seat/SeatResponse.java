package com.example.booking_movie_ticket.dto.response.seat;

import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {
    private Long id;
    private String seatRow;
    private Integer seatNumber;
    private Integer price;
    private Boolean status;
}

