package com.example.booking_movie_ticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking_movie_ticket.domain.User;
import com.example.booking_movie_ticket.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/create")
    public String createNewUser() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("123456");

        this.userService
                .createUser(user);
        return "User created successfully";
    }
}
