package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.PermissionRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.PermissionDetailResponse;
import com.example.booking_movie_ticket.dto.response.user.PermissionResponse;
import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.PermissionRepository;
import com.example.booking_movie_ticket.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    public long createPermission(PermissionRequest request) {
        if (permissionRepository.existsByApiPathIgnoreCaseAndMethodIgnoreCaseAndModuleIgnoreCase(
                request.getApiPath().trim(),
                request.getMethod().trim(),
                request.getModule().trim())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = Permission.builder()
                .name(request.getName().trim())
                .apiPath(request.getApiPath().trim())
                .method(request.getMethod().trim())
                .module(request.getModule().trim())
                .status(true)
                .build();
        Permission saved = permissionRepository.save(permission);
        return saved.getId();
    }

    @Override
    public PageResponse getAllPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> permissionPage = this.permissionRepository.findAll(spec, pageable);
        List<PermissionResponse> permissionList = permissionPage.getContent()
                .stream()
                .map(permission -> PermissionResponse.builder()
                        .id(permission.getId())
                        .name(permission.getName())
                        .apiPath(permission.getApiPath())
                        .method(permission.getMethod())
                        .module(permission.getModule())
                        .status(permission.getStatus())
                        .build()
                ).toList();

        MetaPage metaPage = new MetaPage();
        metaPage.setPage(pageable.getPageNumber() + 1);
        metaPage.setPageSize(pageable.getPageSize());
        metaPage.setPages(permissionPage.getTotalPages());
        metaPage.setTotal(permissionPage.getTotalElements());

        return PageResponse.builder()
                .meta(metaPage)
                .result(permissionList)
                .build();
    }

    @Override
    public PermissionDetailResponse getPermission(long permissionId) {

        // check exist by id
        Permission permission = this.permissionRepository.findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        return PermissionDetailResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .apiPath(permission.getApiPath())
                .method(permission.getMethod())
                .module(permission.getModule())
                .status(permission.getStatus())
                .build();
    }

    @Override
    public void updatePermission(long permissionId, PermissionRequest request) {

        // check exist by id
        Permission permission = this.permissionRepository.findById(permissionId)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_EXISTED));

        // Check trùng với permission khác
        Optional<Permission> existing = permissionRepository
                .findByApiPathIgnoreCaseAndMethodIgnoreCaseAndModuleIgnoreCase(
                        request.getApiPath().trim(),
                        request.getMethod().trim(),
                        request.getModule().trim());

        if (existing.isPresent() &&
                !existing.get().getId().equals(permissionId)) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }


        permission.setName(request.getName().trim());
        permission.setApiPath(request.getApiPath().trim());
        permission.setMethod(request.getMethod().trim());
        permission.setModule(request.getModule().trim());
        permission.setStatus(request.getStatus());

        this.permissionRepository.save(permission);

        log.info("Permission has updated successfully, permissionId={}", permission.getId());
    }
}
