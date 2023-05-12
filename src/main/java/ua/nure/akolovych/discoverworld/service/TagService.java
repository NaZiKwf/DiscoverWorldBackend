package ua.nure.akolovych.discoverworld.service;

import ua.nure.akolovych.discoverworld.dto.AdditionalServicesDto;
import ua.nure.akolovych.discoverworld.dto.TagDto;
import ua.nure.akolovych.discoverworld.entity.AdditionalServicesEntity;
import ua.nure.akolovych.discoverworld.entity.TagEntity;

import java.util.List;
import java.util.UUID;

public interface TagService {

    void deleteTagFromTour(UUID tourId, UUID tagId);

    TagEntity getById(UUID id);

    List<TagEntity> getAll();

    TagEntity create(TagDto tagDto);

    void delete (UUID id);

    List<TagEntity> getAllTagsFromTour(UUID tourId);



}
