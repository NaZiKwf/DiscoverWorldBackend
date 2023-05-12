package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.akolovych.discoverworld.dto.FeedbackCommentsDto;
import ua.nure.akolovych.discoverworld.entity.BookingEntity;
import ua.nure.akolovych.discoverworld.entity.FeedbackCommentsEntity;
import ua.nure.akolovych.discoverworld.entity.TourEntity;
import ua.nure.akolovych.discoverworld.entity.UserEntity;
import ua.nure.akolovych.discoverworld.enumeration.BookingStatus;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.exception.InvalidRequestException;
import ua.nure.akolovych.discoverworld.repository.BookingRepository;
import ua.nure.akolovych.discoverworld.repository.FeedbackCommentsRepository;
import ua.nure.akolovych.discoverworld.repository.TourRepository;
import ua.nure.akolovych.discoverworld.repository.UserRepository;
import ua.nure.akolovych.discoverworld.service.FeedbackCommentsService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackCommentsServiceImpl implements FeedbackCommentsService {

    private final FeedbackCommentsRepository feedbackCommentsRepository;

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final TourRepository tourRepository;

    private final ModelMapper modelMapper;

    @Override
    public FeedbackCommentsEntity getById(UUID id) {
        return feedbackCommentsRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Comment with id '%s' does not exist", id)));
    }

    @Override
    public List<FeedbackCommentsEntity> getAll() {
        return feedbackCommentsRepository.findAll();
    }

    @Override
    @Transactional
    public FeedbackCommentsEntity create(FeedbackCommentsDto feedbackCommentsDto) {
        UserEntity user = userRepository.findById(feedbackCommentsDto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException(String.format(
                        "User with id '%s' does not exist", feedbackCommentsDto.getUserId())));
        TourEntity tour = tourRepository.findById(feedbackCommentsDto.getTourId()).orElseThrow(() ->
                new EntityNotFoundException(String.format(
                        "Tour with id '%s' does not exist", feedbackCommentsDto.getTourId())));
        List<BookingEntity> bookings = bookingRepository.findBookingEntitiesByUserIdAndTourIdAndBookingStatus(
                user.getId(), tour.getId(), BookingStatus.ACCEPTED_BY_ADMIN);
        if(bookings.isEmpty()){
            throw new InvalidRequestException("User don`t have any accepted bookings in this tour");
        }

        FeedbackCommentsEntity comment = modelMapper.map(feedbackCommentsDto, FeedbackCommentsEntity.class);
        comment.setId(null);
        comment.setUser(user);
        comment.setTour(tour);
        feedbackCommentsRepository.save(comment);
        user.getComments().add(comment);
        userRepository.save(user);
        tour.getComments().add(comment);
        tourRepository.save(tour);
        tour.setRating(calculateTourRating(tour));
        tourRepository.save(tour);
        return comment;
    }

    private Double calculateTourRating(TourEntity tour){
        double sumOfEstimates = tour.getComments().stream()
                .map(FeedbackCommentsEntity::getEstimateOfTour)
                .reduce(0.0, Double::sum);
        int sumOfComments = tour.getComments().size();

        return sumOfEstimates / sumOfComments;
    }

    @Override
    public FeedbackCommentsEntity update(FeedbackCommentsDto feedbackCommentsDto) {
        FeedbackCommentsEntity comment = feedbackCommentsRepository.findById(feedbackCommentsDto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Comment with id '%s' does not exist", feedbackCommentsDto.getId())));
        comment.setComment(feedbackCommentsDto.getComment());
        return feedbackCommentsRepository.save(comment);
    }

    @Override
    public void delete(UUID id) {
        if (feedbackCommentsRepository.existsById(id))
            feedbackCommentsRepository.deleteById(id);
    }

    @Override
    public List<FeedbackCommentsEntity> getAllCommentsByTourId(UUID tourId) {
        return feedbackCommentsRepository.findAllCommentsByTourId(tourId);
    }
}
