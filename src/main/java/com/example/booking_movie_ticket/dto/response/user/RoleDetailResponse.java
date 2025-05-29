package com.example.booking_movie_ticket.dto.response.user;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDetailResponse {

    private Long id;
    private String roleName;
    private String description;
    private Boolean status;
    private List<PermissionInfo> permissions;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PermissionInfo{
        private Long id;
        private String name;
    }
}
