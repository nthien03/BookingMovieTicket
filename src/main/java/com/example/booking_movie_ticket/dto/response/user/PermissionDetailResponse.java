package com.example.booking_movie_ticket.dto.response.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionDetailResponse {

    private Long id;

    private String name;

    private String apiPath;

    private String method;

    private String module;

    private Boolean status;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleInfo{
        private Long id;
        private String roleName;
    }
}
