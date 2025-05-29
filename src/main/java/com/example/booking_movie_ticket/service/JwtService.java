package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.response.LoginResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtService {

    String createAccessToken(String username, LoginResponse loginResponse);

    String createRefreshToken(String usernamme, LoginResponse loginResponse);

    Jwt checkValidRefreshToken(String token);

}
