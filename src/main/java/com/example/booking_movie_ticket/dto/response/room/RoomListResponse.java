package com.example.booking_movie_ticket.dto.response.room;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomListResponse {
    private Long id;
    private String roomName;
}
