package ua.nure.akolovych.discoverworld.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponceDto {
    private Boolean existByUsername;
    private Boolean existByEmail;
    private String token;
    private UUID id;
}
