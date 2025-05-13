package com.example.booking_movie_ticket.dto.response;

import com.example.booking_movie_ticket.util.constant.GenderEnum;
import lombok.*;

import java.time.LocalDate;


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
