package ua.nure.akolovych.discoverworld.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.akolovych.discoverworld.dto.AdditionalServicesDto;
import ua.nure.akolovych.discoverworld.dto.TourCategoryDto;
import ua.nure.akolovych.discoverworld.service.AdditionalServicesService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/additional-services")
@RequiredArgsConstructor
public class AdditionalServicesController {

    private final AdditionalServicesService additionalServicesService;

    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public AdditionalServicesDto createAdditionalService(@RequestBody AdditionalServicesDto additionalServicesDto) {
        return modelMapper.map(additionalServicesService.create(additionalServicesDto), AdditionalServicesDto.class);
    }

    @GetMapping("/get/{additionalServicesId}")
    public AdditionalServicesDto getAdditionalServiceById(@PathVariable UUID additionalServicesId) {
        return modelMapper.map(additionalServicesService.getById(additionalServicesId), AdditionalServicesDto.class);
    }


    @DeleteMapping("/delete/{additionalServicesId}")
    public ResponseEntity<Object> deleteAdditionalServiceById(@PathVariable UUID additionalServicesId) {
        additionalServicesService.delete(additionalServicesId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all/additional-services/{tourId}")
    public List<AdditionalServicesDto> getAllAdditionalServicesInTour(@PathVariable UUID tourId){
        return additionalServicesService.getAllAdditionalServicesInTour(tourId).stream()
                .map(additionalServicesEntity -> modelMapper.map(additionalServicesEntity,AdditionalServicesDto.class)).toList();
    }

}
