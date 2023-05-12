package ua.nure.akolovych.discoverworld.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponceDto {
    private Integer status;
    private String message;
    private Long time;
}
