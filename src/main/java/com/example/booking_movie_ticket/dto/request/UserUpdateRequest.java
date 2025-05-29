package com.example.booking_movie_ticket.dto.request;

import com.example.booking_movie_ticket.util.constant.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class UserUpdateRequest {

    @NotBlank(message = "FULL_NAME_BLANK")
    @Size(max = 255, message = "DATA_TOO_LONG")
    private String fullName;

    @Email(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_BLANK")
    private String email;

    @Pattern(regexp = "^(0[0-9]{9})$", message = "PHONE_NUMBER_INVALID")
    @NotBlank(message = "PHONE_NUMBER_BLANK")
    private String phoneNumber;

    @Past(message = "BIRTHDAY_INVALID")
    @NotNull(message = "BIRTHDAY_BLANK")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Size(max = 255, message = "DATA_TOO_LONG")
    private String address;

    private Boolean status;
    @NotNull(message = "ROLE_NULL")
    RoleInfoRequest role;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleInfoRequest{
        private Long id;
    }

}
