package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.RoleRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.RoleDetailResponse;
import com.example.booking_movie_ticket.entity.Role;
import com.example.booking_movie_ticket.service.RoleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Override
    public long createRole(RoleRequest request) {
        return 0;
    }

    @Override
    public PageResponse getAllRoles(Specification<Role> spec, Pageable pageable) {
        return null;
    }

    @Override
    public RoleDetailResponse getRole(long roleId) {
        return null;
    }

    @Override
    public void updateRole(long roleId, RoleRequest request) {

    }
}
