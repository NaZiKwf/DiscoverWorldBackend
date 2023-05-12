package ua.nure.akolovych.discoverworld.entity;




import lombok.*;
import ua.nure.akolovych.discoverworld.enumeration.BookingStatus;
import ua.nure.akolovych.discoverworld.enumeration.HumanSex;


import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Booking")
@Table(name = "bookings")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "human_sex",nullable = false)
    @Enumerated(EnumType.STRING)
    private HumanSex humanSex;

    @Column(name = "date_of_birth", nullable = false)
    private Instant dateOfBirth;

    @Column (name = "citizenship", nullable = false)
    private String citizenship;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "passport_valid_until", nullable = false)
    private Instant passportValidUntil;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus bookingStatus;

    @Column(name = "price")
    private Double price;

    @Column(name = "date_of_booking")
    private Instant dateOfBooking;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private TourEntity tour;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE},mappedBy = "bookings",fetch = FetchType.EAGER)
    private Set<AdditionalServicesEntity> additionalServices = new HashSet<>();
}
