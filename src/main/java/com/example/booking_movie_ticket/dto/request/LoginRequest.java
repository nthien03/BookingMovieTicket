package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "USERNAME_BLANK")
    @Size(max = 255, message = "DATA_TOO_LONG")
    private String username;

    @NotBlank(message = "PASSWORD_BLANK")
    @Size(max = 255, message = "DATA_TOO_LONG")
    private String password;

}
