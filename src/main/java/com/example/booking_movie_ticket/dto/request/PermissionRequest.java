package com.example.booking_movie_ticket.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionRequest {

    @NotBlank(message = "PERMISSION_NAME_BLANK")
    private String name;

    @NotBlank(message = "API_PATH_BLANK")
    private String apiPath;

    @NotBlank(message = "METHOD_BLANK")
    private String method;

    @NotBlank(message = "MODULE_BLANK")
    private String module;

    private Boolean status;
}
