package com.example.booking_movie_ticket.dto.response.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionResponse {

    private Long id;

    private String name;

    private String apiPath;

    private String method;

    private String module;

    private Boolean status;
}
