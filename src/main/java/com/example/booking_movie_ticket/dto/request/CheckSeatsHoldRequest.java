package com.example.booking_movie_ticket.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CheckSeatsHoldRequest {
    @NotNull
    private List<Long> seatIds;
    @NotNull
    private Long scheduleId;
}
