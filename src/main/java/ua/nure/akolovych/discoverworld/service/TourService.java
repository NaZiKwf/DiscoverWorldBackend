package ua.nure.akolovych.discoverworld.service;

import org.springframework.web.multipart.MultipartFile;
import ua.nure.akolovych.discoverworld.dto.RecommendedToursDto;
import ua.nure.akolovych.discoverworld.dto.TourDto;
import ua.nure.akolovych.discoverworld.dto.FilterTourDto;
import ua.nure.akolovych.discoverworld.entity.TourEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TourService {
    TourEntity getById(UUID id);
    List<TourEntity> getAll();
    TourEntity create(TourDto tourDto);
    TourEntity update (TourDto tourDto);
    void delete (UUID id);

    List<TourEntity> findAllPossibleToursForBooking();

    List<TourEntity> getRecommendedTours(RecommendedToursDto recommendedToursDto);

    List<TourEntity> getAllFavoritesUserTours(UUID userId);

    TourEntity editTourProfilePicture(UUID tourId, MultipartFile multipartFile) throws IOException;

    void addTourToFavorites(UUID tourId, UUID userId);
    void deleteTourFromFavorites(UUID tourId, UUID userId);

    List<TourEntity> getAllFilteredTours(FilterTourDto filterTourDto);

    List<TourEntity> getAllToursByCategory(UUID categoryId);

    TourEntity addTagToTour(UUID tourId, UUID tagId);

}
