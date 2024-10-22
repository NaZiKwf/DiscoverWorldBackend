package ua.nure.akolovych.discoverworld.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ua.nure.akolovych.discoverworld.entity.RoleEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class RoleUtils {
    private RoleUtils() {
    }

    public static Collection<? extends GrantedAuthority> mapToGrantedAuthorities(Set<RoleEntity> roles) {
        return roles.stream().map(a -> new SimpleGrantedAuthority(a.getName())).toList();
    }

    public static List<String> mapToStringList(Set<RoleEntity> roles) {
        return roles.stream().map(RoleEntity::getName).toList();
    }

    public static List<String> mapToStringList(Collection<? extends GrantedAuthority> roles) {
        return roles.stream().map(GrantedAuthority::getAuthority).toList();
    }
}
