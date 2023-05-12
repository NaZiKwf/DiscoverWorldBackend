package ua.nure.akolovych.discoverworld.service;

import ua.nure.akolovych.discoverworld.dto.BookingDto;
import ua.nure.akolovych.discoverworld.dto.FeedbackCommentsDto;
import ua.nure.akolovych.discoverworld.entity.BookingEntity;
import ua.nure.akolovych.discoverworld.entity.FeedbackCommentsEntity;

import java.util.List;
import java.util.UUID;

public interface FeedbackCommentsService {
    FeedbackCommentsEntity getById(UUID id);
    List<FeedbackCommentsEntity> getAll();
    FeedbackCommentsEntity create(FeedbackCommentsDto feedbackCommentsDto);

    FeedbackCommentsEntity update(FeedbackCommentsDto feedbackCommentsDto);
    void delete (UUID id);

    List<FeedbackCommentsEntity> getAllCommentsByTourId(UUID tourId);
}
