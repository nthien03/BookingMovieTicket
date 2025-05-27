package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.UserCreateResponse;
import com.example.booking_movie_ticket.dto.response.user.UserDetailResponse;
import com.example.booking_movie_ticket.service.UserService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.dto.request.UserCreateRequest;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserCreateResponse>> createUser(@Valid @RequestBody UserCreateRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<UserCreateResponse>builder()
                        .code(1000)
                        .message("User created successfully")
                        .data(userService.createUser(request))
                        .build());

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDetailResponse>> getUser(@PathVariable long userId) {
        return ResponseEntity.ok().body(
                ApiResponse.<UserDetailResponse>builder()
                        .code(1000)
                        .data(userService.getUserById(userId))
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAllUsers(
            @Filter Specification<User> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok().body(
                ApiResponse.<PageResponse>builder()
                        .code(1000)
                        .data(userService.getAllUsers(spec, pageable))
                        .build());
    }


//    @DeleteMapping("/{userId}")
//    public ResponseEntity<String> deleteUser(@PathVariable long userId) throws IdInvalidException {
//        if (userId > 1500) {
//            throw new IdInvalidException("Id khong lon hon 1500");
//        }
//        this.userService.deleteUser(userId);
//        return ResponseEntity.ok("User deleted successfully");
//    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<User> getUserById(@PathVariable long userId) {
//        return ResponseEntity.ok(this.userService.getUserById(userId));
//        // return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserById(userId));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<User>> getUsers() {
//        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUsers());
//    }

//    @PutMapping("/{userId}")
//    public ResponseEntity<User> updateUser(@PathVariable long userId, @RequestBody UserCreateRequest userRequest) {
//        return ResponseEntity.status(HttpStatus.OK).body(this.userService.updateUser(userId, userRequest));
//    }
}
