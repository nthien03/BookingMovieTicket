package com.example.booking_movie_ticket.service;

import com.example.booking_movie_ticket.dto.request.PermissionRequest;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.PermissionDetailResponse;
import com.example.booking_movie_ticket.entity.Permission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PermissionService {

    long createPermission(PermissionRequest request);
    PageResponse getAllPermissions(Specification<Permission> spec, Pageable pageable);
    PermissionDetailResponse getPermission(long permissionId);
    void updatePermission(long permissionId, PermissionRequest request);
}
