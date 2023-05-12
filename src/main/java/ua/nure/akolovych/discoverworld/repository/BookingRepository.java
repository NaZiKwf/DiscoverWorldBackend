package ua.nure.akolovych.discoverworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.nure.akolovych.discoverworld.entity.BookingEntity;
import ua.nure.akolovych.discoverworld.entity.TourEntity;
import ua.nure.akolovych.discoverworld.enumeration.BookingStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    List <BookingEntity> findAllUsersBookingsByUserId(UUID userId);
    @Query("SELECT booking FROM Booking booking WHERE booking.bookingStatus = ?1")
    List<BookingEntity> findAllAcceptedBookingsByStatus(BookingStatus bookingStatus);
    @Query("SELECT booking FROM Booking booking INNER JOIN Tour tour ON booking.tour.id = tour.id AND tour.id = ?2 " +
            "INNER JOIN User user ON booking.user.id = user.id AND user.id = ?1 WHERE booking.bookingStatus = ?3")
    List<BookingEntity> findBookingEntitiesByUserIdAndTourIdAndBookingStatus(UUID userId, UUID tourId, BookingStatus bookingStatus);
}
