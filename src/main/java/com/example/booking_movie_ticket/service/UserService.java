package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.UserCreateRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.UserCreateResponse;
import com.example.booking_movie_ticket.dto.response.user.UserDetailResponse;
import com.example.booking_movie_ticket.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {
    UserCreateResponse createUser(UserCreateRequest request);

    PageResponse getAllUsers(Specification<User> spec, Pageable pageable);

    UserDetailResponse getUserById(long userId);

    User getUserByUsername(String username);

    void updateUser(long userId, UserCreateRequest request);

    void updateUserToken(String token, String username);

    User getUserByRefreshTokenAndUsername(String refreshToken, String username);


}
