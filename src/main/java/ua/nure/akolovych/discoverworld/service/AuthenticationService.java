package ua.nure.akolovych.discoverworld.service;

import ua.nure.akolovych.discoverworld.dto.auth.AuthenticationRequestDto;
import ua.nure.akolovych.discoverworld.dto.auth.AuthenticationResponceDto;
import ua.nure.akolovych.discoverworld.dto.auth.RegistrationRequestDto;
import ua.nure.akolovych.discoverworld.dto.auth.RegistrationResponceDto;

public interface AuthenticationService {

    AuthenticationResponceDto login(AuthenticationRequestDto requestDTO);
    RegistrationResponceDto register(RegistrationRequestDto requestDTO);
}
