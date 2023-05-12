package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.akolovych.discoverworld.dto.UserDto;
import ua.nure.akolovych.discoverworld.entity.RoleEntity;
import ua.nure.akolovych.discoverworld.entity.UserEntity;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.exception.InvalidRequestException;
import ua.nure.akolovych.discoverworld.repository.RoleRepository;
import ua.nure.akolovych.discoverworld.repository.UserRepository;
import ua.nure.akolovych.discoverworld.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserEntity getById(UUID id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id '%s' does not exist", id)));
    }

    @Override
    public void delete(UUID userId) {
        if (userRepository.existsById(userId))
            userRepository.deleteById(userId);
    }

    @Override
    public UserEntity update(UserDto userDto) {
        UserEntity userEntity = getById(userDto.getId());
        validateUserDtoForUpdate(userDto, userEntity);

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setMiddleName(userDto.getMiddleName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPhone(userDto.getPhone());
        userEntity.setUsername(userDto.getUsername());

        userRepository.save(userEntity);

        return userEntity;
    }

    private void validateUserDtoForUpdate(UserDto userDto, UserEntity user){
        if (Objects.equals(userDto.getUsername().trim(), ""))
            throw new InvalidRequestException("Username cannot be empty");

        if (Objects.equals(userDto.getEmail().trim(), ""))
            throw new InvalidRequestException("Email cannot be empty");

        if(!Objects.equals(userDto.getUsername(), user.getUsername())){
            if (userRepository.existsByUsername(userDto.getUsername()))
                throw new InvalidRequestException(
                        String.format("User with username '%s' already exist", userDto.getUsername()));
        }

        if(!Objects.equals(userDto.getEmail(), user.getEmail())){
            if (userRepository.existsByEmail(userDto.getEmail()))
                throw new InvalidRequestException(
                        String.format("User with email '%s' already exist", userDto.getEmail()));
        }

    }

    private void validateUserDtoForCreate(UserDto userDto) {
        if (Objects.equals(userDto.getUsername().trim(), ""))
            throw new InvalidRequestException("Username cannot be empty");

        if (Objects.equals(userDto.getPassword().trim(), ""))
            throw new InvalidRequestException("Password cannot be empty");

        if (Objects.equals(userDto.getEmail().trim(), ""))
            throw new InvalidRequestException("Email cannot be empty");


        if (userRepository.existsByUsername(userDto.getUsername()))
            throw new InvalidRequestException(
                    String.format("User with username '%s' already exist", userDto.getUsername()));

        if (userRepository.existsByEmail(userDto.getEmail()))
            throw new InvalidRequestException(
                    String.format("User with email '%s' already exist", userDto.getEmail()));
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserEntity create(UserDto userDto) {
        validateUserDtoForCreate(userDto);

        RoleEntity basicRole = roleRepository.findByName("ROLE_USER").orElseThrow(() ->
                new EntityNotFoundException("Base role not found"));

        UserEntity user = modelMapper.map(userDto, UserEntity.class);
        user.setId(null);
        user.getRoles().add(basicRole);
        user = userRepository.save(user);
        basicRole.getUsers().add(user);
        roleRepository.save(basicRole);
        return user;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with username '%s' does not exist", username)));
    }

    @Override
    public Boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserEntity editUserProfilePicture(UUID userId, MultipartFile multipartFile) throws IOException {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id %s does not exist", userId)));

        if(!multipartFile.isEmpty()) {
            byte[] picture = multipartFile.getBytes();
            user.setUserImage(picture);
        }

        return userRepository.save(user);
    }
}
