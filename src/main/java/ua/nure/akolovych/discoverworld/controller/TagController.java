package ua.nure.akolovych.discoverworld.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.akolovych.discoverworld.dto.FeedbackCommentsDto;
import ua.nure.akolovych.discoverworld.dto.TagDto;
import ua.nure.akolovych.discoverworld.service.TagService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public TagDto createTag(@RequestBody TagDto tagDto) {
        return modelMapper.map(tagService.create(tagDto), TagDto.class);
    }

    @GetMapping("/get/{tagId}")
    public TagDto getTagById(@PathVariable UUID tagId) {
        return modelMapper.map(tagService.getById(tagId), TagDto.class);
    }


    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<Object> deleteTagById(@PathVariable UUID tagId) {
        tagService.delete(tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all")
    public List<TagDto> getAllTags(){
        return tagService.getAll().stream()
                .map(tagEntity -> modelMapper.map(tagEntity, TagDto.class)).toList();
    }

    @GetMapping("/get/all-tags-from-tour/{tourId}")
    public  List<TagDto> getAllTagsFromTour(@PathVariable UUID tourId){
        return tagService.getAllTagsFromTour(tourId).stream()
                .map(tagEntity -> modelMapper.map(tagEntity, TagDto.class)).toList();
    }
}
