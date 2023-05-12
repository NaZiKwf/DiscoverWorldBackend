package ua.nure.akolovych.discoverworld.security.userdetails;

import org.springframework.security.core.GrantedAuthority;
import ua.nure.akolovych.discoverworld.dto.UserDto;

import java.util.Collection;
import java.util.Objects;

public class JwtUserFactory {
    public static JwtUser create(UserDto user, Collection<? extends GrantedAuthority> roles) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                roles
        );
    }


}
