package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.akolovych.discoverworld.dto.RecommendedToursDto;
import ua.nure.akolovych.discoverworld.dto.TourDto;
import ua.nure.akolovych.discoverworld.dto.FilterTourDto;
import ua.nure.akolovych.discoverworld.entity.TagEntity;
import ua.nure.akolovych.discoverworld.entity.TourCategoryEntity;
import ua.nure.akolovych.discoverworld.entity.TourEntity;
import ua.nure.akolovych.discoverworld.entity.UserEntity;
import ua.nure.akolovych.discoverworld.exception.EntityExistsException;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.exception.InvalidRequestException;
import ua.nure.akolovych.discoverworld.repository.TagRepository;
import ua.nure.akolovych.discoverworld.repository.TourCategoryRepository;
import ua.nure.akolovych.discoverworld.repository.TourRepository;
import ua.nure.akolovych.discoverworld.repository.UserRepository;
import ua.nure.akolovych.discoverworld.service.TourService;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourRepository tourRepository;
    private final TourCategoryRepository tourCategoryRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public TourEntity getById(UUID id) {
        return tourRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tour already exist"));
    }

    @Override
    public List<TourEntity> getAll() {
        return tourRepository.findAll();
    }

    @Override
    @Transactional
    public TourEntity create(TourDto tourDto) {

        validateTourDtoForCreate(tourDto);

        TourEntity tour = modelMapper.map(tourDto, TourEntity.class);
        tour.setId(null);
        tour.setRating(0.0);
        tour.setStartDate(Instant.ofEpochMilli(tourDto.getStartDate()));
        tour.setEndDate(Instant.ofEpochMilli(tourDto.getEndDate()));


        TourCategoryEntity category = tourCategoryRepository.findById(tourDto.getTourCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Tour category not found"));

        tour.setTourCategory(category);
        tourRepository.save(tour);
        return tour;
    }

    private void validateTourDtoForCreate(TourDto tourDto) {
        if (tourDto.getName() == null || tourDto.getName().trim().isEmpty()) {
            throw new InvalidRequestException("Tour name cannot be empty");
        }

        if (tourDto.getPrice() == null || tourDto.getPrice() <= 0) {
            throw new InvalidRequestException("Tour price must be a positive number");
        }

        if (tourDto.getStartDate() == null || tourDto.getStartDate() <= 0) {
            throw new InvalidRequestException("Tour start date must be a valid timestamp");
        }

        if (tourDto.getEndDate() == null || tourDto.getEndDate() <= 0) {
            throw new InvalidRequestException("Tour end date must be a valid timestamp");
        }

        if (tourDto.getStartDate() >= tourDto.getEndDate()) {
            throw new InvalidRequestException("Tour start date must be earlier than end date");
        }

        if (tourDto.getDescription() == null || tourDto.getDescription().trim().isEmpty()) {
            throw new InvalidRequestException("Tour description cannot be empty");
        }

        if (tourDto.getRating() == null || tourDto.getRating() < 0 || tourDto.getRating() > 5) {
            throw new InvalidRequestException("Tour rating must be a number between 0 and 5");
        }

        if (tourDto.getNumberOfTours() == null || tourDto.getNumberOfTours() <= 0) {
            throw new InvalidRequestException("Tour number of tours must be a positive integer");
        }
    }


    @Override
    @Transactional
    public TourEntity update(TourDto tourDto) {
        TourEntity tour = tourRepository.findById(tourDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Tour with id '%s' does not exist", tourDto.getId())));
        validateTourDtoForCreate(tourDto);
        TourCategoryEntity tourCategory = tourCategoryRepository.findById(tourDto.getTourCategoryId()).orElseThrow(()->
                new EntityNotFoundException("Tour category with id '%s' does not exist"));


        tour.setName(tourDto.getName());
        tour.setDescription(tourDto.getDescription());
        tour.setPrice(tourDto.getPrice());
        tour.setStartDate(Instant.ofEpochMilli(tourDto.getStartDate()));
        tour.setEndDate(Instant.ofEpochMilli(tourDto.getEndDate()));
        tour.setTourCategory(tourCategory);
        tour.setNumberOfTours(tourDto.getNumberOfTours());
        tourCategoryRepository.save(tourCategory);
        tourRepository.save(tour);
        return tour;
    }

    @Override
    public void delete(UUID id) {
        if (tourRepository.existsById(id))
            tourRepository.deleteById(id);
    }

    @Override
    public List<TourEntity> findAllPossibleToursForBooking() {
        return tourRepository.findAll().stream()
                .filter(tourEntity -> tourEntity.getStartDate().isAfter(Instant.now()))
                .toList();
    }

    @Override
    @Transactional
    public List<TourEntity> getRecommendedTours(RecommendedToursDto recommendedToursDto) {
        List<TourEntity> possibleTours = findAllPossibleToursForBooking();

        Map<TourEntity, Long> toursWithCountTagHits = new LinkedHashMap<>();

        for(TourEntity tourEntity : possibleTours){
            isTourHit(tourEntity, recommendedToursDto, toursWithCountTagHits);
        }
        return sortByValue(toursWithCountTagHits).keySet().stream().toList();
    }

    @Override
    public List<TourEntity> getAllFavoritesUserTours(UUID userId) {
        return tourRepository.findAllUserFavouritesTours(userId);
    }

    @Override
    @Transactional
    public TourEntity editTourProfilePicture(UUID tourId, MultipartFile multipartFile) throws IOException {
        TourEntity tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Tour with id %s does not exist", tourId)));

        if(!multipartFile.isEmpty()) {
            byte[] picture = multipartFile.getBytes();
            tour.setTourImage(picture);
        }

        return tourRepository.save(tour);
    }

    @Override
    public void addTourToFavorites(UUID tourId, UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found"));

        if(Objects.nonNull(user)){
            TourEntity tour = tourRepository.findById(tourId).orElseThrow(()->
                    new EntityNotFoundException("Tour not found"));


            if(Objects.nonNull(tour)){
                user.getTours().add(tour);
                tour.getUsers().add(user);
                userRepository.save(user);
                tourRepository.save(tour);
            }

        }
    }

    @Override
    public void deleteTourFromFavorites(UUID tourId, UUID userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found"));

        if(Objects.nonNull(user)){
            TourEntity tour = tourRepository.findById(tourId).orElseThrow(()->
                    new EntityNotFoundException("Tour not found"));


            if(Objects.nonNull(tour)){
                user.getTours().remove(tour);
                tour.getUsers().remove(user);
                userRepository.save(user);
                tourRepository.save(tour);
            }

        }
    }

    @Override
    public List<TourEntity> getAllFilteredTours(FilterTourDto filterTourDto){
        return filterTours(filterTourDto, tourRepository.findAll());
    }


    private List<TourEntity> filterTours(FilterTourDto filterDto, List<TourEntity> source) {
        if (source.isEmpty())
            return source;

        String filterQuery = filterDto.getFilterQuery().trim().toLowerCase();

        if (Objects.equals(filterQuery, ""))
            return source;

        return source.stream()
                .filter(tourEntity -> filterSimpleTour(filterDto, filterQuery, tourEntity))
                .toList();
    }

    @Override
    public List<TourEntity> getAllToursByCategory(UUID categoryId) {
        return tourRepository.findAllToursByCategory(categoryId);
    }

    @Override
    public TourEntity addTagToTour(UUID tourId, UUID tagId) {
        TourEntity tour = tourRepository.findById(tourId).orElseThrow(()->
                new EntityNotFoundException("Tour with id does not exist"));;

        TagEntity tag = tagRepository.findById(tagId).orElseThrow(()->
                new EntityNotFoundException("Tag with id does not exist"));

        tour.getTags().add(tag);
        tag.getTours().add(tour);

        tourRepository.save(tour);
        tagRepository.save(tag);

        return tour;
    }

    private boolean filterSimpleTour(FilterTourDto filterDto, String filterQuery, TourEntity tour) {
        if (filterDto.getFilterName() && tour.getName().toLowerCase().contains(filterQuery))
            return true;

        if (filterDto.getFilterDescription() && tour.getDescription().toLowerCase().contains(filterQuery))
            return true;

        if(filterDto.getFilterCategory() && tour.getTourCategory().getName().toLowerCase().contains(filterQuery)){
            return true;
        }

        return filterDto.getFilterTags() &&
                tour.getTags().stream().anyMatch(tagEntity -> tagEntity.getName().contains(filterQuery));
    }

    private void isTourHit(TourEntity tour, RecommendedToursDto recommendedToursDto, Map<TourEntity, Long> toursWithCountTagHits){

        List<String> tags = Arrays.asList(recommendedToursDto.getTags());
        List<TagEntity> tagsInTour = tour.getTags().stream()
                                                    .toList();

        Long countOfEqualTags = tagsInTour.stream()
                                            .filter(tag -> tags.contains(tag.getName()))
                                            .count();

        toursWithCountTagHits.put(tour, countOfEqualTags);

    }

    private  <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

}
