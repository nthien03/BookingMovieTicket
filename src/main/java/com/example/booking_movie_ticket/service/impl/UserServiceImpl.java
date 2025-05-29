package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.UserCreateRequest;
import com.example.booking_movie_ticket.dto.request.UserUpdateRequest;
import com.example.booking_movie_ticket.dto.response.*;
import com.example.booking_movie_ticket.dto.response.user.UserCreateResponse;
import com.example.booking_movie_ticket.dto.response.user.UserDetailResponse;
import com.example.booking_movie_ticket.dto.response.user.UserListResponse;
import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.entity.Role;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.RoleRepository;
import com.example.booking_movie_ticket.repository.UserRepository;
import com.example.booking_movie_ticket.service.RoleService;
import com.example.booking_movie_ticket.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, RoleService roleService, RoleRepository roleRepository1) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public UserCreateResponse register(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail().trim())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber().trim())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }

        Role role = this.roleRepository.findByRoleNameIgnoreCase("USER")
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));


        User user = User.builder()
                .fullName(request.getFullName().trim())
                .username(request.getUsername().trim())
                .email(request.getEmail().trim())
                .phoneNumber(request.getPhoneNumber().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .address(request.getAddress())
                .role(role)
                .status(true)
                .build();

        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new AppException(ErrorCode.DATA_UNIQUE);
        }

        return new UserCreateResponse(savedUser.getId(), savedUser.getUsername());
    }


    @Override
    public UserCreateResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail().trim())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (userRepository.existsByUsername(request.getUsername().trim())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber().trim())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }

        Role role = null;
        if (request.getRole() != null && request.getRole().getId() != null) {
            role = this.roleRepository.findById(request.getRole().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
        }

        User user = User.builder()
                .fullName(request.getFullName().trim())
                .username(request.getUsername().trim())
                .email(request.getEmail().trim())
                .phoneNumber(request.getPhoneNumber().trim())
                .password(passwordEncoder.encode(request.getPassword()))
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .address(request.getAddress())
                .role(role)
                .status(request.getStatus())
                .build();

        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new AppException(ErrorCode.DATA_UNIQUE);
        }

        return new UserCreateResponse(savedUser.getId(), savedUser.getUsername());
    }

    @Override
    public PageResponse getAllUsers(Specification<User> spec, Pageable pageable) {
        Page<User> userPage = userRepository.findAll(spec, pageable);
        List<UserListResponse> userList = userPage.getContent()
                .stream()
                .map(user -> {
                    UserDetailResponse.RoleInfoResponse roleInfo = null;
                    if (user.getRole() != null) {
                        roleInfo = new UserDetailResponse.RoleInfoResponse(
                                user.getRole().getId(),
                                user.getRole().getRoleName());
                    }

                    return UserListResponse.builder()
                            .id(user.getId())
                            .fullName(user.getFullName())
                            .phoneNumber(user.getPhoneNumber())
                            .email(user.getEmail())
                            .username(user.getUsername())
                            .status(user.getStatus())
                            .role(roleInfo)
                            .build();
                }).toList();
        MetaPage metaPage = new MetaPage();
        metaPage.setPage(pageable.getPageNumber() + 1);
        metaPage.setPageSize(pageable.getPageSize());
        metaPage.setPages(userPage.getTotalPages());
        metaPage.setTotal(userPage.getTotalElements());

        return PageResponse.builder()
                .meta(metaPage)
                .result(userList)
                .build();
    }

    @Override
    public UserDetailResponse getUserById(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        UserDetailResponse.RoleInfoResponse roleInfo = null;
        if (user.getRole() != null) {
            roleInfo = new UserDetailResponse.RoleInfoResponse(user.getRole().getId(), user.getRole().getRoleName());
        }

        return UserDetailResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .username(user.getUsername())
                .birthday(user.getBirthday())
                .address(user.getAddress())
                .gender(user.getGender())
                .role(roleInfo)
                .status(user.getStatus())
                .build();
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    @Override
    public void updateUser(long userId, UserUpdateRequest request) {

        // check exist by id
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Check trùng
        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (!user.getPhoneNumber().equals(request.getPhoneNumber()) &&
                userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }

        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail().trim());
        user.setPhoneNumber(request.getPhoneNumber().trim());
        user.setBirthday(request.getBirthday());
        user.setGender(request.getGender());
        user.setAddress(request.getAddress());
        user.setStatus(request.getStatus());

        this.userRepository.save(user);

        log.info("User has updated successfully, userId={}", user.getId());

    }

    @Override
    public void updateStaff(long userId, UserUpdateRequest request) {

        // check exist by id
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Check trùng
        if (!user.getEmail().equals(request.getEmail()) &&
                userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        if (!user.getPhoneNumber().equals(request.getPhoneNumber()) &&
                userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }

        user.setFullName(request.getFullName().trim());
        user.setEmail(request.getEmail().trim());
        user.setPhoneNumber(request.getPhoneNumber().trim());
        user.setBirthday(request.getBirthday());
        user.setGender(request.getGender());
        user.setAddress(request.getAddress());
        user.setStatus(request.getStatus());

        if (request.getRole() != null && request.getRole().getId() != null) {
            Role role = this.roleRepository.findById(request.getRole().getId())
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

            user.setRole(role);
        }

        this.userRepository.save(user);

        log.info("User has updated successfully, userId={}", user.getId());
    }

    @Override
    public void changeInfo(long userId, UserUpdateRequest request) {

    }

    @Override
    public void updateUserToken(String token, String username) {
        User user = this.getUserByUsername(username);
        user.setRefreshToken(token);
        this.userRepository.save(user);
    }

    @Override
    public User getUserByRefreshTokenAndUsername(String refreshToken, String username) {
        return this.userRepository.findByRefreshTokenAndUsername(refreshToken, username)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_REFRESH_TOKEN));
    }
}
