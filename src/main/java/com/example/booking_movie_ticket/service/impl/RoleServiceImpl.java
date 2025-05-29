package com.example.booking_movie_ticket.service.impl;

import com.example.booking_movie_ticket.dto.request.RoleRequest;
import com.example.booking_movie_ticket.dto.response.MetaPage;
import com.example.booking_movie_ticket.dto.response.PageResponse;
import com.example.booking_movie_ticket.dto.response.user.RoleDetailResponse;
import com.example.booking_movie_ticket.dto.response.user.RoleResponse;
import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.entity.Role;
import com.example.booking_movie_ticket.exception.AppException;
import com.example.booking_movie_ticket.exception.ErrorCode;
import com.example.booking_movie_ticket.repository.PermissionRepository;
import com.example.booking_movie_ticket.repository.RoleRepository;
import com.example.booking_movie_ticket.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public long createRole(RoleRequest request) {
        if (roleRepository.existsByRoleNameIgnoreCase(request.getRoleName().trim())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        List<Permission> dbPermissions = new ArrayList<>();
        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            dbPermissions = permissionRepository.findByIdIn(request.getPermissions());
        }

        Role role = Role.builder()
                .roleName(request.getRoleName().trim())
                .description(request.getDescription())
                .status(true)
                .permissions(dbPermissions)
                .build();

        Role saved = roleRepository.save(role);
        return saved.getId();
    }

    @Override
    public PageResponse getAllRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> rolePage = this.roleRepository.findAll(spec, pageable);
        List<RoleResponse> roleList = rolePage.getContent()
                .stream()
                .map(role -> RoleResponse.builder()
                        .id(role.getId())
                        .roleName(role.getRoleName())
                        .description(role.getDescription())
                        .status(role.getStatus())
                        .build()
                ).toList();

        MetaPage metaPage = new MetaPage();
        metaPage.setPage(pageable.getPageNumber() + 1);
        metaPage.setPageSize(pageable.getPageSize());
        metaPage.setPages(rolePage.getTotalPages());
        metaPage.setTotal(rolePage.getTotalElements());

        return PageResponse.builder()
                .meta(metaPage)
                .result(roleList)
                .build();
    }

    @Override
    public RoleDetailResponse getRole(long roleId) {

        // check exist by id
        Role role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        List<RoleDetailResponse.PermissionInfo> permissions = role
                .getPermissions().stream().map(
                        p -> RoleDetailResponse.PermissionInfo.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .build()
                ).toList();

        return RoleDetailResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName())
                .description(role.getDescription())
                .permissions(permissions)
                .status(role.getStatus())
                .build();
    }

    @Override
    public void updateRole(long roleId, RoleRequest request) {

        // check exist by id
        Role role = this.roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        // Check trùng role
        Optional<Role> existing = roleRepository
                .findByRoleNameIgnoreCase(request.getRoleName().trim());

        if (existing.isPresent() &&
                !existing.get().getId().equals(roleId)) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        List<Permission> dbPermissions = new ArrayList<>();
        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            dbPermissions = permissionRepository.findByIdIn(request.getPermissions());
        }

        role.setRoleName(request.getRoleName().trim());
        role.setDescription(request.getDescription());
        role.setPermissions(dbPermissions);
        role.setStatus(request.getStatus());

        this.roleRepository.save(role);

        log.info("Role has updated successfully, roleId={}", role.getId());

    }
}
