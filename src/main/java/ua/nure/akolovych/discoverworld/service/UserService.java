package ua.nure.akolovych.discoverworld.service;

import org.springframework.web.multipart.MultipartFile;
import ua.nure.akolovych.discoverworld.dto.UserDto;
import ua.nure.akolovych.discoverworld.entity.UserEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


public interface UserService {
    UserEntity getById(UUID id);

    void delete( UUID userId);
    UserEntity update(UserDto userDto);
    List<UserEntity> getAll();
    UserEntity create (UserDto userDto);
    UserEntity getUserByUsername(String username);
    Boolean existsUserByUsername(String username);
    Boolean existsUserByEmail(String email);
    UserEntity editUserProfilePicture(UUID userId, MultipartFile multipartFile) throws IOException;
}
