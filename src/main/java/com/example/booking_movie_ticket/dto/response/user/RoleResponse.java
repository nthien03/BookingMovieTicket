package com.example.booking_movie_ticket.dto.response.user;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {

    private Long id;
    private String roleName;
    private String description;
    private Boolean status;


}
