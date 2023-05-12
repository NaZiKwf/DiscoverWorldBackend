package ua.nure.akolovych.discoverworld.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.akolovych.discoverworld.dto.auth.AuthenticationRequestDto;
import ua.nure.akolovych.discoverworld.dto.auth.AuthenticationResponceDto;
import ua.nure.akolovych.discoverworld.dto.auth.RegistrationRequestDto;
import ua.nure.akolovych.discoverworld.dto.auth.RegistrationResponceDto;
import ua.nure.akolovych.discoverworld.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponceDto login(@RequestBody AuthenticationRequestDto requestDto) {
        return authenticationService.login(requestDto);
    }

    @PostMapping("/register")
    public RegistrationResponceDto register(@RequestBody RegistrationRequestDto requestDto) {
        return authenticationService.register(requestDto);
    }
}
