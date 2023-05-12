package ua.nure.akolovych.discoverworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.akolovych.discoverworld.entity.AdditionalServicesEntity;
import ua.nure.akolovych.discoverworld.enumeration.AdditionalServicesType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdditionalServicesRepository extends JpaRepository<AdditionalServicesEntity, UUID> {
    Optional<AdditionalServicesEntity> findByTourIdAndAdditionalServicesType(UUID tourId, AdditionalServicesType additionalServicesType);

    List<AdditionalServicesEntity> findAllAdditionalServicesByTourId(UUID tourId);
}
