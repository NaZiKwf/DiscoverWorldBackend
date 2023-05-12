package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ua.nure.akolovych.discoverworld.dto.AdditionalServicesDto;
import ua.nure.akolovych.discoverworld.dto.BookingDto;
import ua.nure.akolovych.discoverworld.entity.AdditionalServicesEntity;
import ua.nure.akolovych.discoverworld.entity.BookingEntity;
import ua.nure.akolovych.discoverworld.entity.TagEntity;
import ua.nure.akolovych.discoverworld.entity.TourEntity;
import ua.nure.akolovych.discoverworld.enumeration.AdditionalServicesType;
import ua.nure.akolovych.discoverworld.exception.EntityExistsException;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.exception.InvalidRequestException;
import ua.nure.akolovych.discoverworld.repository.AdditionalServicesRepository;
import ua.nure.akolovych.discoverworld.repository.BookingRepository;
import ua.nure.akolovych.discoverworld.repository.TourRepository;
import ua.nure.akolovych.discoverworld.service.AdditionalServicesService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AdditionalServicesServiceImpl implements AdditionalServicesService {

    private final AdditionalServicesRepository additionalServicesRepository;

    private final TourRepository tourRepository;

    private final BookingRepository bookingRepository;

    private final ModelMapper modelMapper;


    @Override
    public AdditionalServicesEntity getById(UUID id) {
        return additionalServicesRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Additional service with id '%s' does not exist", id)));
    }

    @Override
    public List<AdditionalServicesEntity> getAll() {
        return additionalServicesRepository.findAll();
    }

    @Override
    public AdditionalServicesEntity create(AdditionalServicesDto additionalServicesDto) {

        Optional<AdditionalServicesEntity> existingAdditionalService =
                additionalServicesRepository.findByTourIdAndAdditionalServicesType(additionalServicesDto.getTourId(),
                        additionalServicesDto.getAdditionalServicesType());
        if (existingAdditionalService.isPresent()) {
            throw new EntityExistsException("Additional service with type " + additionalServicesDto.getAdditionalServicesType()
                    + " already exists for a tour");
        }
        TourEntity tour = tourRepository.findById(additionalServicesDto.getTourId()).orElseThrow(()->
                new EntityNotFoundException("Tour with this id not found"));

        AdditionalServicesEntity additionalServicesEntity = new AdditionalServicesEntity();
        additionalServicesEntity.setTour(tour);
        additionalServicesEntity.setName(additionalServicesDto.getName());
        additionalServicesEntity.setPrice(additionalServicesDto.getPrice());
        additionalServicesEntity.setAdditionalServicesType(additionalServicesDto.getAdditionalServicesType());

        return additionalServicesRepository.save(additionalServicesEntity);
    }

    @Override
    public List<AdditionalServicesEntity> getAllAdditionalServicesInTour(UUID tourId) {
        return additionalServicesRepository.findAllAdditionalServicesByTourId(tourId);
    }


    @Override
    public void delete(UUID id) {
        if (additionalServicesRepository.existsById(id))
            additionalServicesRepository.deleteById(id);
    }
}
