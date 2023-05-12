package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.akolovych.discoverworld.dto.TagDto;
import ua.nure.akolovych.discoverworld.entity.TagEntity;
import ua.nure.akolovych.discoverworld.entity.TourEntity;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.exception.InvalidRequestException;
import ua.nure.akolovych.discoverworld.repository.TagRepository;
import ua.nure.akolovych.discoverworld.repository.TourRepository;
import ua.nure.akolovych.discoverworld.service.TagService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;
    private final TourRepository tourRepository;


    @Override
    public void deleteTagFromTour(UUID tourId, UUID tagId) {
        TourEntity tour = tourRepository.findById(tourId).orElseThrow(()->
                new EntityNotFoundException(String.format("Tour with id '%s' does not exist", tourId)));

        TagEntity tag = tagRepository.findById(tagId).orElseThrow(()->
                new EntityNotFoundException(String.format("Tag with id '%s' does not exist", tagId)));

        tour.getTags().remove(tag);
        tag.getTours().remove(tour);

        tagRepository.save(tag);
        tourRepository.save(tour);

    }

    @Override
    public TagEntity getById(UUID id) {
        return tagRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Tag with id '%s' does not exist", id)));
    }

    @Override
    public List<TagEntity> getAll() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public TagEntity create(TagDto tagDto) {
        tagDto.setName(tagDto.getName().trim().toLowerCase());

        if(Objects.equals(tagDto.getName(), ""))
            throw new InvalidRequestException("Name cannot be empty");

        if (!tagDto.getName().startsWith("#"))
            tagDto.setName("#" + tagDto.getName());

        if (tagRepository.existsByName(tagDto.getName()))
            return tagRepository.findByName(tagDto.getName()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Tag with name '%s' does not exist", tagDto.getName())));

        TagEntity tag = modelMapper.map(tagDto, TagEntity.class);
        tag.setId(null);
        return tagRepository.save(tag);
    }

    @Override
    public void delete(UUID id) {
        if (tagRepository.existsById(id))
            tagRepository.deleteById(id);
    }

    @Override
    public List<TagEntity> getAllTagsFromTour(UUID tourId) {
        TourEntity tour = tourRepository.findById(tourId).orElseThrow(()->
                new EntityNotFoundException("Tour with this id not found"));

        return tour.getTags().stream().toList();
    }


}
