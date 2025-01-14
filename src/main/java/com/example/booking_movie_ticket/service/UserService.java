package com.example.booking_movie_ticket.service;

import org.springframework.stereotype.Service;

import com.example.booking_movie_ticket.domain.User;
import com.example.booking_movie_ticket.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {

        return this.userRepository.save(user);
    }
}
