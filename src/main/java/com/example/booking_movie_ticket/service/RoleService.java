package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.RoleRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.RoleDetailResponse;
import com.example.booking_movie_ticket.dto.response.user.RoleResponse;
import com.example.booking_movie_ticket.entity.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface RoleService {

    long createRole(RoleRequest request);
    PageResponse getAllRoles(Specification<Role> spec, Pageable pageable);
    RoleDetailResponse getRole(long roleId);
    void updateRole(long roleId, RoleRequest request);
}
