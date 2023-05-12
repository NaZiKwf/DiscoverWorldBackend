package ua.nure.akolovych.discoverworld.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.nure.akolovych.discoverworld.enumeration.BookingStatus;
import ua.nure.akolovych.discoverworld.enumeration.HumanSex;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String userPhone;

    private String userEmail;

    private BookingStatus bookingStatus;

    private HumanSex humanSex;

    private Long dateOfBirth;

    private String citizenship;

    private String passportNumber;

    private Long passportValidUntil;

    private Double price;

    private Long dateOfBooking;

    private UUID userId;

    private UUID tourId;

}
