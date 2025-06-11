package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.request.LoginRequest;
import com.example.booking_movie_ticket.dto.request.RegisterRequest;
import com.example.booking_movie_ticket.dto.request.UserCreateRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.LoginResponse;
import com.example.booking_movie_ticket.dto.response.user.RoleDetailResponse;
import com.example.booking_movie_ticket.dto.response.user.UserCreateResponse;
import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.entity.Role;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.service.JwtService;
import com.example.booking_movie_ticket.service.UserService;
import com.example.booking_movie_ticket.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtService jwtService, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtService = jwtService;
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserCreateResponse>> register(@Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<UserCreateResponse>builder()
                        .code(1000)
                        .message("User created successfully")
                        .data(userService.register(request))
                        .build());

    }


    private RoleDetailResponse mapToRoleResponse(Role role) {

        if (role == null) return null;

        List<Permission> permissionList = role.getPermissions();
        if (permissionList == null) permissionList = List.of(); // fallback empty

        List<RoleDetailResponse.PermissionInfo> permissions = permissionList.stream()
                .map(p -> RoleDetailResponse.PermissionInfo.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .build())
                .toList();

        return RoleDetailResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .permissions(permissions)
                .status(role.getStatus())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        //Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        //xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // set thong tin ng dung dang nhap vao context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponse loginResponse = new LoginResponse();

        User currentUser = this.userService.getUserByUsername(loginRequest.getUsername());

        // mapper response
        RoleDetailResponse role= this.mapToRoleResponse(currentUser.getRole());

        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getFullName(),
                role);

        loginResponse.setUser(userLogin);

        // create access token
        String access_token = this.jwtService.createAccessToken(authentication.getName(), loginResponse);

        loginResponse.setAccessToken(access_token);

        // create refresh token
        String refresh_token = this.jwtService.createRefreshToken(loginRequest.getUsername(), loginResponse);

        // update user
        this.userService.updateUserToken(refresh_token, loginRequest.getUsername());
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();


        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(
                        ApiResponse.<LoginResponse>builder()
                                .code(1000)
                                .message("Call api success")
                                .data(loginResponse)
                                .build()
                );
    }

    @GetMapping("/account")
    public ResponseEntity<ApiResponse<LoginResponse.UserGetAccount>> getAccount() {
        String username = SecurityUtil.getCurrentUserLogin().orElse("");

        User currentUser = this.userService.getUserByUsername(username);

        // mapper response
        RoleDetailResponse role= this.mapToRoleResponse(currentUser.getRole());

        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getFullName(),
                role);

        return ResponseEntity.ok(
                ApiResponse.<LoginResponse.UserGetAccount>builder()
                        .code(1000)
                        .data(new LoginResponse.UserGetAccount(userLogin))
                        .build()
        );
    }

    @GetMapping("refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> getRefreshToken(
            @CookieValue(name = "refresh_token") String refresh_token) {

        // check valid
        Jwt decodedToken = jwtService.checkValidRefreshToken(refresh_token);

        String username = decodedToken.getSubject();

        // check user by token and username
        User currentUser = userService.getUserByRefreshTokenAndUsername(refresh_token, username);

        LoginResponse loginResponse = new LoginResponse();

        // mapper response
        RoleDetailResponse role= this.mapToRoleResponse(currentUser.getRole());

        LoginResponse.UserLogin userLogin = new LoginResponse.UserLogin(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getFullName(),
                role);

        loginResponse.setUser(userLogin);
        // create access token
        String access_token = this.jwtService.createAccessToken(username, loginResponse);

        loginResponse.setAccessToken(access_token);

        // create refresh token
        String new_refresh_token = this.jwtService.createRefreshToken(username, loginResponse);

        // update user
        this.userService.updateUserToken(new_refresh_token, username);

        //set cookies
        ResponseCookie responseCookie = ResponseCookie
                .from("refresh_token", new_refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();


        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(
                        ApiResponse.<LoginResponse>builder()
                                .code(1000)
                                .message("Call api success")
                                .data(loginResponse)
                                .build()
                );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {

        String username = SecurityUtil.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN_ERROR));

        // update refresh token = null
        this.userService.updateUserToken(null, username);

        // remove refresh token in cookie

        ResponseCookie deleteCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(ApiResponse.<Void>builder()
                        .code(1000)
                        .message("Logout thành công!")
                        .build());


    }


}
