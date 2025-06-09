package com.example.booking_movie_ticket.dto.response.seat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SeatStatusResponse {
    private Long seatId;
    private String seatRow;
    private Integer seatNumber;
    private Integer price;
    private String seatTypeName;
    private Boolean hold;

}
