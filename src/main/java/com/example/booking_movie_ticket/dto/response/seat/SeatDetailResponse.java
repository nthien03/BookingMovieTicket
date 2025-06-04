package com.example.booking_movie_ticket.dto.response.seat;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatDetailResponse {

    private Long id;

    private String seatRow;

    private Integer seatNumber;

    private Integer price;

    private Boolean status;

    private SeatTypeInfo seatType;

    private RoomInfo room;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SeatTypeInfo {
        private Long id;
        private String seatTypeName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoomInfo {
        private Long id;
        private String roomName;
    }
}


