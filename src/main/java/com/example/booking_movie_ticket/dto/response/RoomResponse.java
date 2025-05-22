package com.example.booking_movie_ticket.dto.response;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomName;
    private Boolean status;
    private Integer total_row;
    private Integer total_column;
}
