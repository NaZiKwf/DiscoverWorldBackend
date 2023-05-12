package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.akolovych.discoverworld.entity.RoleEntity;
import ua.nure.akolovych.discoverworld.entity.UserEntity;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.repository.RoleRepository;
import ua.nure.akolovych.discoverworld.repository.UserRepository;
import ua.nure.akolovych.discoverworld.service.RoleService;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    @Override
    public RoleEntity getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() ->
                new EntityNotFoundException(String.format("Authority with name '%s' does not exist", roleName)));
    }

    @Override
    public Boolean isUserAdmin(UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(()->
                new EntityNotFoundException("User with this id not found"));

        return user.getRoles().stream()
                .anyMatch(roleEntity -> roleEntity.getName().equals("ROLE_ADMIN"));
    }
}
