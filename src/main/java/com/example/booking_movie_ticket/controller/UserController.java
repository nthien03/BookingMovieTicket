package com.example.booking_movie_ticket.controller;

import java.util.List;

import com.example.booking_movie_ticket.exception.IdInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.dto.request.UserRequest;
import com.example.booking_movie_ticket.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createNewUser(@RequestBody UserRequest userRequest) {

        User user = this.userService
                .createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) throws IdInvalidException {
        if (userId > 1500){
            throw new IdInvalidException("Id khong lon hon 1500");
        }
        this.userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
        // return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.getUsers());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable long userId, @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.updateUser(userId, userRequest));
    }
}
