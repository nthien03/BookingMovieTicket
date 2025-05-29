package com.example.booking_movie_ticket.repository;


import com.example.booking_movie_ticket.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>,
        JpaSpecificationExecutor<Permission> {

    boolean existsByApiPathAndMethodAndModule(String apiPath, String method, String module);
    Optional<Permission> findByApiPathAndMethodAndModule(String apiPath, String method, String module);

    boolean existsByApiPathIgnoreCaseAndMethodIgnoreCaseAndModuleIgnoreCase(
            String apiPath, String method, String module
    );
    Optional<Permission> findByApiPathIgnoreCaseAndMethodIgnoreCaseAndModuleIgnoreCase(
            String apiPath, String method, String module);

}
