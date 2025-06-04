package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatRequest {
    @NotBlank
    private String seatRow;

    @NotNull
    private Integer seatNumber;

    @NotNull
    private Integer price;

    @NotNull
    private Long seatTypeId;

    @NotNull
    private Long roomId;

    private Boolean status;
}

