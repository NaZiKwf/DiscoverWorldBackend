package ua.nure.akolovych.discoverworld.entity;


import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Tour")
@Table(name = "tours")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "tour_image")
    private byte[] tourImage;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "number_of_tours")
    private Integer numberOfTours;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_category_id", referencedColumnName = "id")
    private TourCategoryEntity tourCategory;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<FeedbackCommentsEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<BookingEntity> booking = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<UserEntity> users = new HashSet<>();

    @OneToMany(mappedBy = "tour", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<AdditionalServicesEntity> additionalActivities = new HashSet<>();

    @ManyToMany(mappedBy = "tours", fetch = FetchType.LAZY)
    private Set<TagEntity> tags = new HashSet<>();
}
