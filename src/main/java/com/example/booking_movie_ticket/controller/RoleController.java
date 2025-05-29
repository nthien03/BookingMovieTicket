package com.example.booking_movie_ticket.controller;


import com.example.booking_movie_ticket.dto.request.PermissionRequest;
import com.example.booking_movie_ticket.dto.request.RoleRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.PermissionDetailResponse;
import com.example.booking_movie_ticket.dto.response.user.RoleDetailResponse;
import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.entity.Role;
import com.example.booking_movie_ticket.service.RoleService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createRole(@Valid @RequestBody RoleRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<Long>builder()
                        .code(1000)
                        .message("Role created successfully")
                        .data(roleService.createRole(request)).build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAllRoles(
            @Filter Specification<Role> spec,
            Pageable pageable) {

        return ResponseEntity.ok().body(
                ApiResponse.<PageResponse>builder()
                        .code(1000)
                        .data(roleService.getAllRoles(spec, pageable))
                        .build());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> getRole(
            @PathVariable long roleId) {

        return ResponseEntity.ok().body(
                ApiResponse.<RoleDetailResponse>builder()
                        .code(1000)
                        .data(roleService.getRole(roleId))
                        .build());
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<ApiResponse<Void>> updateRole(
            @PathVariable long roleId,
            @Valid @RequestBody RoleRequest request) {

        this.roleService.updateRole(roleId, request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(1000)
                .message("Role updated successfully")
                .build());
    }
}
