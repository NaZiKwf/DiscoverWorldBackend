package ua.nure.akolovych.discoverworld.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.nure.akolovych.discoverworld.enumeration.AdditionalServicesType;


import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "additional_services")
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalServicesEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "additional_services_type", nullable = false)
    private AdditionalServicesType additionalServicesType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private TourEntity tour;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "additional_service_booking",
            joinColumns = @JoinColumn(name = "additional_service_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id",referencedColumnName = "id"))
    private Set<BookingEntity> bookings;

}
