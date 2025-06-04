package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.PaymentRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.booking.PaymentResponse;
import com.example.booking_movie_ticket.dto.response.payment.VNPayResponse;
import com.example.booking_movie_ticket.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/vn-pay")
    public ResponseEntity<ApiResponse<VNPayResponse>> pay(HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponse.<VNPayResponse>builder()
                .code(1000)
                .data(paymentService.createVnPayPayment(request))
                .build());
    }
//    @GetMapping("/vn-pay-callback")
//    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
//        String status = request.getParameter("vnp_ResponseCode");
//        if (status.equals("00")) {
//            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
//        } else {
//            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
//        }
//    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(@RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<Long>builder().code(1000).message("Payment created").data(paymentService.createPayment(request)).build()
        );
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ApiResponse.<PaymentResponse>builder()
                .code(1000).data(paymentService.getByBooking(bookingId)).build());
    }
}

