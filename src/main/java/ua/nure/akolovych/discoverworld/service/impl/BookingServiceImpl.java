package ua.nure.akolovych.discoverworld.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.nure.akolovych.discoverworld.dto.BookingDto;
import ua.nure.akolovych.discoverworld.entity.AdditionalServicesEntity;
import ua.nure.akolovych.discoverworld.entity.BookingEntity;
import ua.nure.akolovych.discoverworld.entity.TourEntity;
import ua.nure.akolovych.discoverworld.entity.UserEntity;
import ua.nure.akolovych.discoverworld.enumeration.BookingStatus;
import ua.nure.akolovych.discoverworld.exception.EntityNotFoundException;
import ua.nure.akolovych.discoverworld.exception.InvalidRequestException;
import ua.nure.akolovych.discoverworld.repository.AdditionalServicesRepository;
import ua.nure.akolovych.discoverworld.repository.BookingRepository;
import ua.nure.akolovych.discoverworld.repository.TourRepository;
import ua.nure.akolovych.discoverworld.repository.UserRepository;
import ua.nure.akolovych.discoverworld.service.BookingService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final AdditionalServicesRepository additionalServicesRepository;

    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final TourRepository tourRepository;


    @Override
    public BookingEntity getById(UUID id) {
      return  bookingRepository.findById(id).orElseThrow(() ->
              new EntityNotFoundException(String.format("Booking with id '%s' does not exist", id)));
    }

    @Override
    public List<BookingEntity> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public BookingEntity createBooking(BookingDto bookingDto) {
        TourEntity tour = tourRepository.findById(bookingDto.getTourId()).orElseThrow(()->
                new EntityNotFoundException("Tour doesn't exist"));

        UserEntity user = userRepository.findById(bookingDto.getUserId()).orElseThrow(() ->
                new EntityNotFoundException("User doesn't exist"));

        if(tour.getStartDate().isBefore(Instant.now())){
            throw new InvalidRequestException("Booking date must before start date");
        }


        if ( bookingDto.getFirstName() == null || bookingDto.getFirstName().isEmpty() || bookingDto.getLastName() == null ||
                bookingDto.getLastName().isEmpty() || bookingDto.getUserPhone() == null || bookingDto.getUserPhone().isEmpty() ||
                bookingDto.getDateOfBirth() == null || bookingDto.getCitizenship() == null || bookingDto.getCitizenship().isEmpty() ||
                bookingDto.getPassportNumber() == null || bookingDto.getPassportNumber().isEmpty() ||
                bookingDto.getHumanSex() == null || bookingDto.getPassportValidUntil() == null ||
                bookingDto.getUserEmail() == null || bookingDto.getUserEmail().isEmpty()) {
            throw new IllegalArgumentException("One or more required fields are missing");
        }


        BookingEntity booking = BookingEntity.builder()
                .user(user)
                .tour(tour)
                .firstName(bookingDto.getFirstName())
                .lastName(bookingDto.getLastName())
                .citizenship(bookingDto.getCitizenship())
                .passportNumber(bookingDto.getPassportNumber())
                .humanSex(bookingDto.getHumanSex())
                .dateOfBirth(Instant.ofEpochMilli(bookingDto.getDateOfBirth()))
                .passportValidUntil(Instant.ofEpochMilli(bookingDto.getPassportValidUntil()))
                .price(bookingDto.getPrice())
                .userEmail(bookingDto.getUserEmail())
                .userPhone(bookingDto.getUserPhone())
                .bookingStatus(BookingStatus.ACCEPTED_BY_USER)
                .dateOfBooking(Instant.now())
                .build();

        booking = bookingRepository.save(booking);

        return booking;
    }

    @Override
    public void addAdditionalServiceToBooking(UUID additionalServiceId, UUID bookingId) {
        AdditionalServicesEntity additionalServices = additionalServicesRepository.findById(additionalServiceId)
                .orElseThrow (()-> (
                        new EntityNotFoundException(String.format("Additional service with id '%s' does not exist", additionalServiceId))));

        BookingEntity booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> (new EntityNotFoundException(String.format("Booking with id '%s' does not exist", bookingId))));

        additionalServices.getBookings().add(booking);
        booking.getAdditionalServices().add(additionalServices);
        additionalServicesRepository.save(additionalServices);
        bookingRepository.save(booking);
    }

    @Override
    public BookingEntity updateBookingStatusByAdmin(UUID bookingId, BookingStatus bookingStatus) {
        BookingEntity booking = getById(bookingId);

        booking.setBookingStatus(bookingStatus);

        bookingRepository.save(booking);

        return booking;
    }

    @Override
    public List<BookingEntity> findAllAcceptedUserBookings(BookingStatus bookingStatus) {
        return bookingRepository.findAllAcceptedBookingsByStatus(bookingStatus);
    }

    @Override
    public List<BookingEntity> findAllUsersBooking(UUID userId) {
        return bookingRepository.findAllUsersBookingsByUserId(userId);
    }


    @Override
    public void delete(UUID id) {
        if (bookingRepository.existsById(id))
            bookingRepository.deleteById(id);
    }
}
