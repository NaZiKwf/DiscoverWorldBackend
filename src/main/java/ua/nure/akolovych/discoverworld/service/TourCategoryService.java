package ua.nure.akolovych.discoverworld.service;

import ua.nure.akolovych.discoverworld.dto.BookingDto;
import ua.nure.akolovych.discoverworld.dto.TourCategoryDto;
import ua.nure.akolovych.discoverworld.entity.BookingEntity;
import ua.nure.akolovych.discoverworld.entity.TourCategoryEntity;

import java.util.List;
import java.util.UUID;

public interface TourCategoryService {
    TourCategoryEntity getById(UUID id);
    List<TourCategoryEntity> getAll();
    TourCategoryEntity create(TourCategoryDto tourCategoryDto);
    TourCategoryEntity update (TourCategoryDto tourCategoryDto);
    void delete (UUID id);
}
