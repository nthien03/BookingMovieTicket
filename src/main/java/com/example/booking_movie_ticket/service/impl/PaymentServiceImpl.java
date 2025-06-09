package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.config.VNPAYConfig;

import com.example.booking_movie_ticket.dto.response.payment.VNPayResponse;

import com.example.booking_movie_ticket.service.PaymentService;
import com.example.booking_movie_ticket.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final VNPAYConfig vnpayConfig;

    public PaymentServiceImpl(VNPAYConfig vnpayConfig) {
        this.vnpayConfig = vnpayConfig;
    }

//    public static boolean verifySignature(Map<String, String[]> paramMap, String hashSecret) {
//        // Lấy secure hash VNPay gửi qua
//        String secureHash = Optional.ofNullable(paramMap.get("vnp_SecureHash"))
//                .map(arr -> arr.length > 0 ? arr[0] : null)
//                .orElse(null);
//        if (secureHash == null) {
//            return false;
//        }
//
//
//        Map<String, String[]> signFields = new HashMap<>(paramMap);
//        signFields.remove("vnp_SecureHash");
//        signFields.remove("vnp_SecureHashType");
//
//        // 2. Tạo chuỗi dữ liệu cần ký
//        String data = buildData(signFields);
//
//        // 3. Tính toán HMAC SHA512
//        String calculatedHash = hmacSHA512(hashSecret, data);
//
//        // 4. So sánh không phân biệt hoa thường
//        return secureHash.equalsIgnoreCase(calculatedHash);
//    }
//
//    /**
//     * Ghép chuỗi <code>field1=value1&field2=value2...</code> theo alphabet, URL-encode value.
//     */
//    private static String buildData(Map<String, String[]> fields) {
//        List<String> fieldNames = new ArrayList<>(fields.keySet());
//        Collections.sort(fieldNames);
//
//        StringBuilder sb = new StringBuilder();
//        for (String name : fieldNames) {
//            String[] values = fields.get(name);
//            if (values != null && values.length > 0 && values[0] != null && !values[0].isEmpty()) {
//                sb.append(name)
//                        .append('=')
//                        .append(URLEncoder.encode(values[0], StandardCharsets.US_ASCII));
//                sb.append('&');
//            }
//        }
//        // Xoá dấu & cuối
//        if (sb.length() > 0) sb.setLength(sb.length() - 1);
//        return sb.toString();
//    }

    @Override
    public VNPayResponse createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        String orderId = request.getParameter("orderId");

        Map<String, String> vnpParamsMap = vnpayConfig.getVNPayConfig(orderId);
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

