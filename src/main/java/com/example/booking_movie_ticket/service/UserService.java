package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.UserCreateRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.UserCreateResponse;
import com.example.booking_movie_ticket.dto.response.UserDetailResponse;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {
    UserCreateResponse createUser(UserCreateRequest request);
    PageResponse getAllUsers(Specification<User> spec, Pageable pageable);
    UserDetailResponse getUserById(long userId);
    User getUserByUsername(String username);
    void updateAUser(long userId, UserCreateRequest request);

}
