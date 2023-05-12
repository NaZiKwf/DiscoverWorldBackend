package ua.nure.akolovych.discoverworld.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.akolovych.discoverworld.dto.FeedbackCommentsDto;
import ua.nure.akolovych.discoverworld.service.FeedbackCommentsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class FeedbackCommentsController {
    private final FeedbackCommentsService feedbackCommentsService;

    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public FeedbackCommentsDto createComment(@RequestBody FeedbackCommentsDto feedbackCommentsDto) {
        return modelMapper.map(feedbackCommentsService.create(feedbackCommentsDto), FeedbackCommentsDto.class);
    }

    @GetMapping("/get/{commentId}")
    public FeedbackCommentsDto getCommentById(@PathVariable UUID commentId) {
        return modelMapper.map(feedbackCommentsService.getById(commentId), FeedbackCommentsDto.class);
    }

    @PatchMapping("/update")
    public FeedbackCommentsDto updateComment(@RequestBody FeedbackCommentsDto feedbackCommentsDto) {
        return modelMapper.map(feedbackCommentsService.update(feedbackCommentsDto), FeedbackCommentsDto.class);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Object> deleteCommentById(@PathVariable UUID commentId) {
        feedbackCommentsService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("get-all/comments/{tourId}")
    public List<FeedbackCommentsDto> getAllCommentsFromTour(@PathVariable UUID tourId){
        return feedbackCommentsService.getAllCommentsByTourId(tourId).stream()
                .map(feedbackCommentsEntity -> modelMapper.map(feedbackCommentsEntity, FeedbackCommentsDto.class)).toList();

    }
}
