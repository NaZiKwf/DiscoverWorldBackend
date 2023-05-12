package ua.nure.akolovych.discoverworld.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponceDto {
    private Integer status;
    private String message;
    private Instant time;
}
