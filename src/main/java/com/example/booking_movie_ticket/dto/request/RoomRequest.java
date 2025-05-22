package com.example.booking_movie_ticket.dto.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoomRequest {
    private String roomName;
    private Boolean status;
    private Integer total_row;
    private Integer total_column;
}
