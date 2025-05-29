package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class RoleRequest {

    @NotBlank(message = "ROLE_NAME_BLANK")
    private String roleName;
    private String description;
    private Boolean status ;

    private List<Long> permissions;

}
