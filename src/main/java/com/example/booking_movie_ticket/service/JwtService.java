package com.example.booking_movie_ticket.service;

import org.springframework.security.core.Authentication;

public interface JwtService {

    String createToken(Authentication authentication);
}
