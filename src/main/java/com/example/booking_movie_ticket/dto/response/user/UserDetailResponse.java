package com.example.booking_movie_ticket.dto.response.user;

import com.example.booking_movie_ticket.util.constant.GenderEnum;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {

    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String phoneNumber;

    private LocalDate birthday;

    private GenderEnum gender;

    private String address;

    private Boolean status;

}
