package ua.nure.akolovych.discoverworld.service;

import ua.nure.akolovych.discoverworld.dto.BookingDto;
import ua.nure.akolovych.discoverworld.entity.BookingEntity;
import ua.nure.akolovych.discoverworld.enumeration.BookingStatus;

import java.util.List;
import java.util.UUID;

public interface BookingService {
    BookingEntity getById(UUID id);
    List<BookingEntity> getAll();
    BookingEntity createBooking(BookingDto bookingDto);
    void addAdditionalServiceToBooking(UUID additionalServiceId, UUID bookingId);

    BookingEntity updateBookingStatusByAdmin(UUID bookingId, BookingStatus bookingStatus);

    List<BookingEntity> findAllAcceptedUserBookings(BookingStatus bookingStatus);

    List<BookingEntity> findAllUsersBooking(UUID userId);
    void delete (UUID id);
}
