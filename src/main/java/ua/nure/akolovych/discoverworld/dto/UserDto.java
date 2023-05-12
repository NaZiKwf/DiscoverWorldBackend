package ua.nure.akolovych.discoverworld.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private byte[] userImage;
    private Long created;
    private Long updated;
    private String username;
    private String password;
}
