package ua.nure.akolovych.discoverworld.service;

import ua.nure.akolovych.discoverworld.entity.RoleEntity;

import java.util.UUID;

public interface RoleService {
    RoleEntity getRoleByName(String roleName);

    Boolean isUserAdmin(UUID userId);
}
