package com.example.booking_movie_ticket.service;

import java.util.List;

import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.dto.request.UserCreateRequest;
import com.example.booking_movie_ticket.repository.UserRepository;

@Service
public class UserServicev1 {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServicev1(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public User createUser(UserCreateRequest userRequest) {
//
//        User user = new User();
//        user.setFullName(userRequest.getName());
//        user.setEmail(userRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//
//        return this.userRepository.save(user);
//    }

    public void deleteUser(long userId) {
        this.userRepository.deleteById(userId);
    }

    public User getUserById(long userId) {

        return this.userRepository.findById(userId).orElse(null);
    }

    public User getUserByUsername(String username) {

        return this.userRepository.findByUsername(username).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    public List<User> getUsers() {

        return this.userRepository.findAll();
    }

//    public User updateUser(long userId, UserCreateRequest userRequest) {
//        User user = getUserById(userId);
//        if (user == null) {
//            return null;
//        }
//        user.setFullName(userRequest.getName());
//        user.setEmail(userRequest.getEmail());
//        user.setPassword(userRequest.getPassword());
//
//        return this.userRepository.save(user);
//    }
}
