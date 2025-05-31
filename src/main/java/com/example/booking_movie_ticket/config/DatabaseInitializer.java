package com.example.booking_movie_ticket.config;

import com.example.booking_movie_ticket.entity.Permission;
import com.example.booking_movie_ticket.entity.Role;
import com.example.booking_movie_ticket.entity.User;
import com.example.booking_movie_ticket.repository.PermissionRepository;
import com.example.booking_movie_ticket.repository.RoleRepository;
import com.example.booking_movie_ticket.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DatabaseInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> Run init database <<<");

        long countPermissions = permissionRepository.count();
        long countRoles = roleRepository.count();
        long countUsers = userRepository.count();

        if (countPermissions == 0) {
            ArrayList<Permission> arr = new ArrayList<>();

            arr.add(new Permission("Create a movie", "/api/v1/movies", "POST", "MOVIES", true));
            arr.add(new Permission("Update a movie", "/api/v1/movies/{movieId}", "PUT", "MOVIES", true));
            arr.add(new Permission("Get a movie by id", "/api/v1/movies/{movieId}", "GET", "MOVIES", true));
            arr.add(new Permission("Get movies with pagination", "/api/v1/movies", "GET", "MOVIES", true));
            arr.add(new Permission("Get movies now showing", "/api/v1/movies/now-showing", "GET", "MOVIES", true));

            // ACTORS
            arr.add(new Permission("Create an actor", "/api/v1/actors", "POST", "ACTORS", true));
            arr.add(new Permission("Update an actor", "/api/v1/actors/{actorId}", "PUT", "ACTORS", true));
            arr.add(new Permission("Get an actor by id", "/api/v1/actors/{actorId}", "GET", "ACTORS", true));
            arr.add(new Permission("Get actors with pagination", "/api/v1/actors", "GET", "ACTORS", true));

            // GENRES
            arr.add(new Permission("Create a genre", "/api/v1/genres", "POST", "GENRES", true));
            arr.add(new Permission("Update a genre", "/api/v1/genres/{genreId}", "PUT", "GENRES", true));
            arr.add(new Permission("Get a genre by id", "/api/v1/genres/{genreId}", "GET", "GENRES", true));
            arr.add(new Permission("Get genres with pagination", "/api/v1/genres", "GET", "GENRES", true));

            // PERMISSIONS
            arr.add(new Permission("Create a permission", "/api/v1/permissions", "POST", "PERMISSIONS", true));
            arr.add(new Permission("Update a permission", "/api/v1/permissions/{permissionId}", "PUT", "PERMISSIONS", true));
            arr.add(new Permission("Get a permission by id", "/api/v1/permissions/{permissionId}", "GET", "PERMISSIONS", true));
            arr.add(new Permission("Get permissions with pagination", "/api/v1/permissions", "GET", "PERMISSIONS", true));

            // ROLES
            arr.add(new Permission("Create a role", "/api/v1/roles", "POST", "ROLES", true));
            arr.add(new Permission("Update a role", "/api/v1/roles/{roleId}", "PUT", "ROLES", true));
            arr.add(new Permission("Get a role by id", "/api/v1/roles/{roleId}", "GET", "ROLES", true));
            arr.add(new Permission("Get roles with pagination", "/api/v1/roles", "GET", "ROLES", true));

            // ROOMS
            arr.add(new Permission("Create a room", "/api/v1/rooms", "POST", "ROOMS", true));
            arr.add(new Permission("Update a room", "/api/v1/rooms/{roomId}", "PUT", "ROOMS", true));
            arr.add(new Permission("Get a room by id", "/api/v1/rooms/{roomId}", "GET", "ROOMS", true));
            arr.add(new Permission("Get rooms with pagination", "/api/v1/rooms", "GET", "ROOMS", true));

            // SEATS
            arr.add(new Permission("Create a seat", "/api/v1/seats", "POST", "SEATS", true));
            arr.add(new Permission("Update a seat", "/api/v1/seats/{seatId}", "PUT", "SEATS", true));
            arr.add(new Permission("Get a seat by id", "/api/v1/seats/{seatId}", "GET", "SEATS", true));
            arr.add(new Permission("Get seats with pagination", "/api/v1/seats", "GET", "SEATS", true));

            // SCHEDULES
            arr.add(new Permission("Create a schedule", "/api/v1/schedules", "POST", "SCHEDULES", true));
            arr.add(new Permission("Update a schedule", "/api/v1/schedules/{scheduleId}", "PUT", "SCHEDULES", true));
            arr.add(new Permission("Get a schedule by id", "/api/v1/schedules/{scheduleId}", "GET", "SCHEDULES", true));
            arr.add(new Permission("Get schedules with pagination", "/api/v1/schedules", "GET", "SCHEDULES", true));

            // SEAT TYPES
            arr.add(new Permission("Create a seat type", "/api/v1/seat-types", "POST", "SEAT_TYPES", true));
            arr.add(new Permission("Update a seat type", "/api/v1/seat-types/{seatTypeId}", "PUT", "SEAT_TYPES", true));
            arr.add(new Permission("Get a seat type by id", "/api/v1/seat-types/{seatTypeId}", "GET", "SEAT_TYPES", true));
            arr.add(new Permission("Get seat types with pagination", "/api/v1/seat-types", "GET", "SEAT_TYPES", true));

            // USERS
            arr.add(new Permission("Create a user", "/api/v1/users", "POST", "USERS", true));
            arr.add(new Permission("Update a user", "/api/v1/users/{userId}", "PUT", "USERS", true));
            arr.add(new Permission("Get a user by id", "/api/v1/users/{userId}", "GET", "USERS", true));
            arr.add(new Permission("Get users with pagination", "/api/v1/users", "GET", "USERS", true));

            // FILES
            arr.add(new Permission("UPLOAD a user", "/api/v1/files", "POST", "FILES", true));

            permissionRepository.saveAll(arr);

        }

        if (countRoles == 0) {
            List<Permission> allPermissions = permissionRepository.findAll();

            Role adminRole = new Role();
            adminRole.setRoleName("SUPER_ADMIN");
            adminRole.setDescription("có full quyền trong hệ thống");
            adminRole.setStatus(true);
            adminRole.setPermissions(allPermissions);

            roleRepository.save(adminRole);
        }

        if (countUsers == 0){
            User adminUser = new User();
            adminUser.setFullName("Super admin");
            adminUser.setUsername("super_admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPhoneNumber("0000000000");
            adminUser.setStatus(true);
            adminUser.setPassword(passwordEncoder.encode("123456"));

            Role adminRole = roleRepository.findByRoleNameIgnoreCase("SUPER_ADMIN").orElse(null);
            adminUser.setRole(adminRole);

            userRepository.save(adminUser);
        }

        if (countUsers > 0 && countPermissions > 0 && countRoles > 0){
            System.out.println(">>> database exist <<<");
        }else
            System.out.println(">>> End init database <<<");
    }
}
