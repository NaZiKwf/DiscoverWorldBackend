package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.akolovych.discoverworld.dto.UserDto;
import ua.nure.akolovych.discoverworld.dto.auth.AuthenticationRequestDto;
import ua.nure.akolovych.discoverworld.dto.auth.AuthenticationResponceDto;
import ua.nure.akolovych.discoverworld.dto.auth.RegistrationRequestDto;
import ua.nure.akolovych.discoverworld.dto.auth.RegistrationResponceDto;
import ua.nure.akolovych.discoverworld.entity.UserEntity;
import ua.nure.akolovych.discoverworld.security.jwt.JwtTokenProvider;
import ua.nure.akolovych.discoverworld.service.AuthenticationService;
import ua.nure.akolovych.discoverworld.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public AuthenticationResponceDto login(AuthenticationRequestDto requestDto) {
        UserEntity user = userService.getUserByUsername(requestDto.getUsername());

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Invalid username or password");

        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        return new AuthenticationResponceDto(token, user.getId());
    }

    @Override
    public RegistrationResponceDto register(RegistrationRequestDto requestDto) {
        RegistrationResponceDto responseDto = new RegistrationResponceDto();
        responseDto.setExistByUsername(userService.existsUserByUsername(requestDto.getUsername()));
        responseDto.setExistByEmail(userService.existsUserByEmail(requestDto.getEmail()));

        if (responseDto.getExistByUsername() || responseDto.getExistByEmail())
            return responseDto;

        UserDto userDto = new UserDto();
        userDto.setUsername(requestDto.getUsername());
        userDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userDto.setEmail(requestDto.getEmail());

        UserEntity user = userService.create(userDto);
        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
        responseDto.setToken(token);
        responseDto.setId(user.getId());
        return responseDto;
    }
}
