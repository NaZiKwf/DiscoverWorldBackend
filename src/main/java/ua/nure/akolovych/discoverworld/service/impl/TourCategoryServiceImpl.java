package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ua.nure.akolovych.discoverworld.dto.TourCategoryDto;
import ua.nure.akolovych.discoverworld.entity.BookingEntity;
import ua.nure.akolovych.discoverworld.entity.TourCategoryEntity;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.exception.InvalidRequestException;
import ua.nure.akolovych.discoverworld.repository.TourCategoryRepository;
import ua.nure.akolovych.discoverworld.service.TourCategoryService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TourCategoryServiceImpl implements TourCategoryService {

    private final TourCategoryRepository tourCategoryRepository;

    private final ModelMapper modelMapper;


    @Override
    public TourCategoryEntity getById(UUID id) {
        return tourCategoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Tour category with id '%s' does not exist", id)));
    }

    @Override
    public List<TourCategoryEntity> getAll() {
        return tourCategoryRepository.findAll();
    }

    @Override
    public TourCategoryEntity create(TourCategoryDto tourCategoryDto) {
        tourCategoryDto.setName(tourCategoryDto.getName().trim().toLowerCase());

        if(Objects.equals(tourCategoryDto.getName(), ""))
            throw new InvalidRequestException("Name cannot be empty");

        if (tourCategoryRepository.existsByName(tourCategoryDto.getName()))
            return tourCategoryRepository.findByName(tourCategoryDto.getName()).orElseThrow(() ->
                    new EntityNotFoundException(String.format("Tour category with name '%s' does not exist", tourCategoryDto.getName())));

        TourCategoryEntity tourCategory = modelMapper.map(tourCategoryDto, TourCategoryEntity.class);
        tourCategory.setId(null);
        return tourCategoryRepository.save(tourCategory);
    }

    @Override
    public TourCategoryEntity update(TourCategoryDto tourCategoryDto) {
        TourCategoryEntity tourCategory = tourCategoryRepository.findById(tourCategoryDto.getId()).orElseThrow(()->
                new EntityNotFoundException("Tour category with id '%s' does not exist"));

        tourCategoryDto.setName(tourCategoryDto.getName().trim().toLowerCase());

        if(Objects.equals(tourCategoryDto.getName(), ""))
            throw new InvalidRequestException("Name cannot be empty");

      tourCategory.setName(tourCategoryDto.getName());
      tourCategoryRepository.save(tourCategory);

        return tourCategory;
    }


    @Override
    public void delete(UUID id) {
        if (tourCategoryRepository.existsById(id))
            tourCategoryRepository.deleteById(id);
    }
}
