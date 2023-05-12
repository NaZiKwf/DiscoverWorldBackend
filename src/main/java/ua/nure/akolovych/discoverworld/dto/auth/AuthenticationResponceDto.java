package ua.nure.akolovych.discoverworld.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponceDto {
    private String token;
    private UUID id;
}
