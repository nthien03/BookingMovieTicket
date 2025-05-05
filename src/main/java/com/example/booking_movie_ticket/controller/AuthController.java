package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.request.LoginRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginRequest>> login(@RequestBody LoginRequest loginRequest) {
        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        //xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return ResponseEntity.ok().body(
                ApiResponse.<LoginRequest>builder()
                        .code(1000)
                        .message("call api success")
                        .data(loginRequest)
                        .build());
    }
}
