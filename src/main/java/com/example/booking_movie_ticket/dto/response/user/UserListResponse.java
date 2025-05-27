package com.example.booking_movie_ticket.dto.response.user;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {

    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String phoneNumber;

}
