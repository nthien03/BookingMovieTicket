package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.PaymentRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.booking.PaymentResponse;
import com.example.booking_movie_ticket.dto.response.payment.VNPayResponse;
import com.example.booking_movie_ticket.entity.Booking;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.BookingRepository;
import com.example.booking_movie_ticket.service.BookingService;
import com.example.booking_movie_ticket.service.PaymentService;
import com.example.booking_movie_ticket.service.TicketService;
import com.example.booking_movie_ticket.util.constant.BookingStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final TicketService ticketService;

    public PaymentController(PaymentService paymentService, BookingRepository bookingRepository, BookingService bookingService, TicketService ticketService) {
        this.paymentService = paymentService;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.ticketService = ticketService;
    }


    @GetMapping("/vn-pay")
    public ResponseEntity<ApiResponse<VNPayResponse>> pay(HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponse.<VNPayResponse>builder()
                .code(1000)
                .data(paymentService.createVnPayPayment(request))
                .build());
    }
    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse res) throws IOException {
        String responseCode = request.getParameter("vnp_ResponseCode");
        String transactionStatus = request.getParameter("vnp_TransactionStatus");
        String orderId = request.getParameter("vnp_TxnRef");

        String redirectBaseUrl = "http://localhost:3000/payment/result";

        if (responseCode == null || transactionStatus == null || orderId == null) {
            throw new AppException(ErrorCode.MISSING_REQUIRED_PARAMETERS);
        }

        Booking booking = bookingRepository.findByBookingCode(orderId).orElse(null);
        if (booking == null) {
            res.sendRedirect(redirectBaseUrl + "?status=invalid");
            return;
        }

        if(booking.getStatus() == BookingStatus.BOOKED.getValue() ||
                booking.getStatus() == BookingStatus.COMPLETED.getValue() ){
            res.sendRedirect(redirectBaseUrl + "?status=success&orderId=" + orderId);
        }

        if ("00".equals(responseCode) && "00".equals(transactionStatus)) {
            bookingService.updateBookingStatus(orderId, BookingStatus.BOOKED.getValue());
            ticketService.updateTicketStatusByBookingId(booking.getId(), true);
            res.sendRedirect(redirectBaseUrl + "?status=success&orderId=" + orderId);
        } else {
            bookingService.updateBookingStatus(orderId, BookingStatus.PAYMENT_FAILED.getValue());
            res.sendRedirect(redirectBaseUrl + "?status=failed&orderId=" + orderId);
        }
    }

//    @PostMapping
//    public ResponseEntity<ApiResponse<Long>> create(@RequestBody PaymentRequest request) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(
//                ApiResponse.<Long>builder().code(1000).message("Payment created").data(paymentService.createPayment(request)).build()
//        );
//    }

//    @GetMapping("/booking/{bookingId}")
//    public ResponseEntity<ApiResponse<PaymentResponse>> getByBooking(@PathVariable Long bookingId) {
//        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
//                .code(1000).data(paymentService.getByBooking(bookingId)).build());
//    }
}

