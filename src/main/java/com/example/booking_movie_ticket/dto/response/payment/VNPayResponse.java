package com.example.booking_movie_ticket.dto.response.payment;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VNPayResponse {
    public String code;
    public String message;
    public String paymentUrl;
}
