package ua.nure.akolovych.discoverworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.akolovych.discoverworld.entity.TourCategoryEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TourCategoryRepository extends JpaRepository<TourCategoryEntity, UUID> {


    boolean existsByName(String name);

    Optional<TourCategoryEntity> findByName(String name);
}
