package ua.nure.akolovych.discoverworld.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.akolovych.discoverworld.dto.TagDto;
import ua.nure.akolovych.discoverworld.dto.TourCategoryDto;
import ua.nure.akolovych.discoverworld.service.TourCategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class TourCategoryController {

    private final TourCategoryService tourCategoryService;

    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public TourCategoryDto createCategory(@RequestBody TourCategoryDto categoryDto) {
        return modelMapper.map(tourCategoryService.create(categoryDto), TourCategoryDto.class);
    }

    @GetMapping("/get/{categoryId}")
    public TourCategoryDto getCategoryById(@PathVariable UUID categoryId) {
        return modelMapper.map(tourCategoryService.getById(categoryId), TourCategoryDto.class);
    }

    @PatchMapping("/update")
    public TourCategoryDto updateCategory(@RequestBody TourCategoryDto categoryDto) {
        return modelMapper.map(tourCategoryService.update(categoryDto), TourCategoryDto.class);


    }

    @GetMapping("/get-all")
    public List<TagDto> getAllCategories(){
        return tourCategoryService.getAll().stream()
                .map(tagEntity -> modelMapper.map(tagEntity, TagDto.class)).toList();
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Object> deleteCategoryById(@PathVariable UUID categoryId) {
        tourCategoryService.delete(categoryId);
        return ResponseEntity.noContent().build();
    }


}
