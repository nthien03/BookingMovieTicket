package com.example.booking_movie_ticket.controller;

import com.example.booking_movie_ticket.dto.request.PermissionRequest;
import com.example.booking_movie_ticket.dto.response.ApiResponse;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.PermissionDetailResponse;
import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.service.PermissionService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPermission(@Valid @RequestBody PermissionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<Long>builder()
                        .code(1000)
                        .message("Permission created successfully")
                        .data(permissionService.createPermission(request)).build()
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse>> getAllPermissions(
            @Filter Specification<Permission> spec,
            Pageable pageable) {

        return ResponseEntity.ok().body(
                ApiResponse.<PageResponse>builder()
                        .code(1000)
                        .data(permissionService.getAllPermissions(spec, pageable))
                        .build());
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<PermissionDetailResponse>> getPermission(
            @PathVariable long permissionId) {

        return ResponseEntity.ok().body(
                ApiResponse.<PermissionDetailResponse>builder()
                        .code(1000)
                        .data(permissionService.getPermission(permissionId))
                        .build());
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<ApiResponse<Void>> updatePermission(
            @PathVariable long permissionId,
            @Valid @RequestBody PermissionRequest request) {

        this.permissionService.updatePermission(permissionId, request);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .code(1000)
                .message("Permission updated successfully")
                .build());
    }
}
