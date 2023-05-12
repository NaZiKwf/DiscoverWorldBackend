package ua.nure.akolovych.discoverworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.akolovych.discoverworld.entity.TagEntity;

import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<TagEntity, UUID> {

    boolean existsByName(String name);

    Optional<TagEntity> findByName(String name);

    Optional<TagEntity> findById(UUID tagId);
}
