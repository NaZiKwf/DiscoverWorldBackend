package ua.nure.akolovych.discoverworld.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.akolovych.discoverworld.dto.BookingDto;
import ua.nure.akolovych.discoverworld.dto.FeedbackCommentsDto;
import ua.nure.akolovych.discoverworld.enumeration.BookingStatus;
import ua.nure.akolovych.discoverworld.service.BookingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public BookingDto createBooking(@RequestBody BookingDto bookingDto) {
        return modelMapper.map(bookingService.createBooking(bookingDto), BookingDto.class);
    }

    @GetMapping("/get/{bookingId}")
    public BookingDto getBookingById(@PathVariable UUID bookingId) {
        return modelMapper.map(bookingService.getById(bookingId), BookingDto.class);
    }


    @DeleteMapping("/delete/{bookingId}")
    public ResponseEntity<Object> deleteBookingById(@PathVariable UUID bookingId) {
        bookingService.delete(bookingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/update/bookingStatus/{bookingId}/{bookingStatus}")
    public BookingDto updateBookingStatus(@PathVariable UUID bookingId, @PathVariable BookingStatus bookingStatus){
        return modelMapper.map(bookingService.updateBookingStatusByAdmin(bookingId, bookingStatus), BookingDto.class);
    }

    @PostMapping("/add/additional-services/{additionalServiceId}/{bookingId}")
    public ResponseEntity<Object> addAdditionalServicesToBooking(@PathVariable UUID additionalServiceId, @PathVariable UUID bookingId){
        bookingService.addAdditionalServiceToBooking(additionalServiceId, bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get-all/accepted-booking-by-user/{bookingStatus}")
    public List<BookingDto> getAllAcceptedBookingsByUser(@PathVariable BookingStatus bookingStatus){
        return bookingService.findAllAcceptedUserBookings(bookingStatus).stream()
                .map(bookingEntity -> modelMapper.map(bookingEntity, BookingDto.class)).toList();
    }

    @GetMapping("/get-all/users-bookings/{userId}")
    public List <BookingDto> getAllUsersBooking (@PathVariable UUID userId){
        return bookingService.findAllUsersBooking(userId).stream()
                .map(bookingEntity -> modelMapper.map(bookingEntity, BookingDto.class)).toList();
    }
}
