package ua.nure.akolovych.discoverworld.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.akolovych.discoverworld.dto.UserDto;
import ua.nure.akolovych.discoverworld.dto.auth.AuthenticationRequestDto;
import ua.nure.akolovych.discoverworld.dto.auth.AuthenticationResponceDto;
import ua.nure.akolovych.discoverworld.entity.UserEntity;
import ua.nure.akolovych.discoverworld.security.jwt.JwtTokenProvider;
import ua.nure.akolovych.discoverworld.service.RoleService;
import ua.nure.akolovych.discoverworld.service.UserService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    private final RoleService roleService;

    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public UserDto create(@RequestBody UserDto userDto) {
        return modelMapper.map(userService.create(userDto), UserDto.class);
    }

    @GetMapping("/get/{userId}")
    public UserDto getUserById(@PathVariable UUID userId) {
        return modelMapper.map(userService.getById(userId), UserDto.class);
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Object> deleteUserById(@PathVariable UUID userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public AuthenticationResponceDto update(@RequestBody UserDto userDto) {
        UserEntity user = userService.update(userDto);
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        return new AuthenticationResponceDto(token, user.getId());
    }


    @PatchMapping("/update/user-picture-profile/{userId}/picture")
    @ApiOperation(value = "Edit user picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserDto editUserPicture(
            @PathVariable("userId") UUID userId,
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException {
       return modelMapper.map(userService.editUserProfilePicture(userId, multipartFile), UserDto.class);
    }

    @GetMapping("/get/users-roles/{userId}")
    public Boolean getUsersRoles(@PathVariable UUID userId) {
        return roleService.isUserAdmin(userId);
    }


}
