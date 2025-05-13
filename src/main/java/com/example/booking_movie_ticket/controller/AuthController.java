package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.request.LoginRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.LoginResponse;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.service.JwtService;
import com.example.booking_movie_ticket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtService jwtService, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        //xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // create a token
        String access_token = this.jwtService.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User currentUser = this.userService.getUserByUsername(loginRequest.getUsername());

        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getFullName());

        return ResponseEntity.ok().body(
                ApiResponse.<LoginResponse>builder()
                        .code(1000)
                        .message("Call api success")
                        .data(new LoginResponse(access_token, userLogin))
                        .build());
    }
}
