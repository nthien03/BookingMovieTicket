package com.example.booking_movie_ticket.repository;


import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    boolean existsByRoleNameIgnoreCase(String roleName);

    Optional<Role> findByRoleNameIgnoreCase(String roleName);


}
