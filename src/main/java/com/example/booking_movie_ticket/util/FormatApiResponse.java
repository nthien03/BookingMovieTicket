//package com.example.booking_movie_ticket.util;
//
//import com.example.booking_movie_ticket.dto.response.ApiResponse;
//import jakarta.servlet.Servlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.http.server.ServletServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
//@ControllerAdvice
//public class FormatApiResponse implements ResponseBodyAdvice<Object> {
//    @Override
//    public boolean supports(MethodParameter returnType, Class converterType) {
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(
//            Object body,
//            MethodParameter returnType,
//            MediaType selectedContentType,
//            Class selectedConverterType,
//            ServerHttpRequest request,
//            ServerHttpResponse response) {
//        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
//        int status = servletResponse.getStatus();
//
//        ApiResponse<Object> apiResponse = new ApiResponse<Object>();
//        apiResponse.setStatusCode(status);
//
//        // case error
//        if (status >= 400) {
//            return body;
//        } else {
//            // case sucess
//            apiResponse.setData(body);
//        }
//        return apiResponse;
//    }
//}
