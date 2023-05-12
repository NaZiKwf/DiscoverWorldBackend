package ua.nure.akolovych.discoverworld.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.akolovych.discoverworld.dto.RecommendedToursDto;
import ua.nure.akolovych.discoverworld.dto.TourDto;
import ua.nure.akolovych.discoverworld.dto.FilterTourDto;
import ua.nure.akolovych.discoverworld.service.TagService;
import ua.nure.akolovych.discoverworld.service.TourService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    private final TagService tagService;

    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public TourDto createTour(@RequestBody TourDto tourDto) {
        return modelMapper.map(tourService.create(tourDto), TourDto.class);
    }

    @GetMapping("/get/{tourId}")
    public TourDto getTourById(@PathVariable UUID tourId) {
        return modelMapper.map(tourService.getById(tourId), TourDto.class);
    }

    @PatchMapping("/update")
    public TourDto updateTour(@RequestBody TourDto tourDto) {
        return modelMapper.map(tourService.update(tourDto), TourDto.class);
    }

    @DeleteMapping("/delete/{tourId}")
    public ResponseEntity<Object> deleteTourById(@PathVariable UUID tourId) {
        tourService.delete(tourId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all/possible-tours-for-booking")
    public List<TourDto> getAllPossibleToursForBooking(){
        return tourService.findAllPossibleToursForBooking().stream()
                .map(tour -> modelMapper.map(tour, TourDto.class))
                .toList();
    }

    @PostMapping("/get-all/filtered")
    public List<TourDto> getAllFilteredTours(@RequestBody FilterTourDto filterTourDto) {
        return tourService.getAllFilteredTours(filterTourDto).stream()
                .map(tourEntity -> modelMapper.map(tourEntity, TourDto.class)).toList();
    }

    @GetMapping ("/get-all/by-category/{categoryId}")
    public List<TourDto> getAllToursByCategory(@PathVariable UUID categoryId){
        return tourService.getAllToursByCategory(categoryId).stream()
                .map(tourEntity -> modelMapper.map(tourEntity, TourDto.class )).toList();
    }

    @PostMapping("/get-all/recommended-tours")
    public List<TourDto> getAllRecommendedTours(@RequestBody RecommendedToursDto recommendedToursDto){
        return tourService.getRecommendedTours(recommendedToursDto).stream()
                .map(tour -> modelMapper.map(tour, TourDto.class)).toList();
    }

    @PatchMapping("/update/tour-picture-profile/{tourId}/picture")
    @ApiOperation(value = "Edit tour picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TourDto editTourPicture(
            @PathVariable("tourId") UUID tourId,
            @RequestPart("file") MultipartFile multipartFile
    ) throws IOException {
        return modelMapper.map(tourService.editTourProfilePicture(tourId, multipartFile), TourDto.class);
    }

    @PostMapping("/add/tag/{tagId}/{tourId}")
    public ResponseEntity<Object> addTagToTour(@PathVariable UUID tourId, @PathVariable UUID tagId){
        tourService.addTagToTour(tourId, tagId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add/tour-to-favourite/{tourId}/{userId}")
    public ResponseEntity<Object> addTourToFavourite(@PathVariable UUID tourId, @PathVariable UUID userId){
        tourService.addTourToFavorites(tourId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/tag-from-tour/{tourId}/{tagId}")
    public ResponseEntity<Object> deleteTagFromTour(@PathVariable UUID tourId, @PathVariable UUID tagId){
        tagService.deleteTagFromTour(tourId, tagId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/tour-from-favourite/{tourId}/{userId}")
    public ResponseEntity<Object> deleteTourFromFavourite(@PathVariable UUID tourId, @PathVariable UUID userId){
        tourService.deleteTourFromFavorites(tourId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all/favorites/{userId}")
    public List<TourDto> getAllUserFavoritesTours(@PathVariable UUID userId){
        return tourService.getAllFavoritesUserTours(userId).stream()
                .map(tourEntity -> modelMapper.map(tourEntity, TourDto.class)).toList();
    }
}
