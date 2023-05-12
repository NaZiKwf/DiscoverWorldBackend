package ua.nure.akolovych.discoverworld.dto.auth;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String username;

    private String password;

    private String email;
}
