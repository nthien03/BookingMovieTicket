package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.config.VNPAYConfig;
import com.example.booking_movie_ticket.dto.request.PaymentRequest;
import com.example.booking_movie_ticket.dto.response.booking.PaymentResponse;
import com.example.booking_movie_ticket.dto.response.payment.VNPayResponse;
import com.example.booking_movie_ticket.entity.Booking;
import com.example.booking_movie_ticket.entity.Payment;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.BookingRepository;
import com.example.booking_movie_ticket.repository.PaymentRepository;
import com.example.booking_movie_ticket.service.PaymentService;
import com.example.booking_movie_ticket.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final VNPAYConfig vnpayConfig;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingRepository bookingRepository, VNPAYConfig vnpayConfig) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.vnpayConfig = vnpayConfig;
    }

    @Override
    public Long createPayment(PaymentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new AppException(ErrorCode.BOOKING_NOT_EXISTED));

        Payment payment = Payment.builder()
                .method(request.getMethod())
                .paymentCode(request.getPaymentCode())
                .amount(request.getAmount())
                .paymentDate(Instant.now())
                .status(request.getStatus())
                .booking(booking)
                .build();

        return paymentRepository.save(payment).getPaymentId();
    }

    @Override
    public PaymentResponse getByBooking(Long bookingId) {
        Payment p = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_NOT_EXISTED));

        return PaymentResponse.builder()
                .paymentId(p.getPaymentId())
                .method(p.getMethod())
                .paymentCode(p.getPaymentCode())
                .paymentDate(p.getPaymentDate())
                .amount(p.getAmount())
                .status(p.getStatus())
                .bookingId(p.getBooking().getId())
                .build();
    }

    @Override
    public VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }
}

