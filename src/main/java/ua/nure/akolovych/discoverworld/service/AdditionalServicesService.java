package ua.nure.akolovych.discoverworld.service;

import ua.nure.akolovych.discoverworld.dto.AdditionalServicesDto;
import ua.nure.akolovych.discoverworld.entity.AdditionalServicesEntity;
import ua.nure.akolovych.discoverworld.enumeration.AdditionalServicesType;

import java.util.List;
import java.util.UUID;

public interface AdditionalServicesService {
    AdditionalServicesEntity getById(UUID id);
    List<AdditionalServicesEntity> getAll();
    AdditionalServicesEntity create(AdditionalServicesDto additionalServicesDto);
    List<AdditionalServicesEntity> getAllAdditionalServicesInTour(UUID tourId);
    void delete (UUID id);
}
