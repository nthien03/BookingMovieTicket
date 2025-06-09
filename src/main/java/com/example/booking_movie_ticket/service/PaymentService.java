package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.PaymentRequest;
import com.example.booking_movie_ticket.dto.response.booking.PaymentResponse;
import com.example.booking_movie_ticket.dto.response.payment.VNPayResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
//    Long createPayment(PaymentRequest request);
//    PaymentResponse getByBooking(Long bookingId);
    public VNPayResponse createVnPayPayment(HttpServletRequest request);
}

