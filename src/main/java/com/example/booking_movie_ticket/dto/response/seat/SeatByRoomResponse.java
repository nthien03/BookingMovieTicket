package com.example.booking_movie_ticket.dto.response.seat;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatByRoomResponse {
    private Long id;
    private Integer seatNumber;
    private String seatRow;
    private Integer price;
    private Boolean status;
    private SeatTypeInSeat seatType;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatTypeInSeat {
        private Long id;
        private String seatTypeName;
    }
}
