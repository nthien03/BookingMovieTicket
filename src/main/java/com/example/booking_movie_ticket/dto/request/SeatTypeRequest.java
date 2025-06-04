package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatTypeRequest {

    @NotBlank
    private String seatTypeName;

    @NotNull
    private Integer price;

    private Boolean status;
}
