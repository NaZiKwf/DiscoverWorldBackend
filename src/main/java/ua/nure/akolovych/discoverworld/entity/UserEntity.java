package ua.nure.akolovych.discoverworld.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name = "User")
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "image")
    private byte[] userImage;

    @Column(name = "phone_number")
    private String phone;

    @Column(name = "email")
    private String email;
    @CreationTimestamp
    @Column(name = "created")
    private Instant created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Instant updated;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE},mappedBy = "users",fetch = FetchType.EAGER)
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<FeedbackCommentsEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<BookingEntity> bookings = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE},mappedBy = "users",fetch = FetchType.EAGER)
    private Set<TourEntity> tours = new HashSet<>();
}
