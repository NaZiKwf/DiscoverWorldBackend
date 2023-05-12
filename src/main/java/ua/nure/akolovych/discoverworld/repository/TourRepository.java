package ua.nure.akolovych.discoverworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.nure.akolovych.discoverworld.entity.TourEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TourRepository extends JpaRepository<TourEntity, UUID> {
    @Query(nativeQuery = true, value = "SELECT tours.* FROM tours " +
            "INNER JOIN favorites " +
            "ON tours.id = favorites.tour_id " +
            "WHERE favorites.user_id = ?1 ")
    List<TourEntity> findAllUserFavouritesTours(UUID userId);
    Optional<TourEntity> findByName(String name);

    @Query( nativeQuery = true, value = "SELECT * FROM tours t " +
            "INNER JOIN tour_category tc ON t.category_id = tc.id  WHERE tc.id = ?1")
    List<TourEntity> findAllToursByCategory(UUID categoryId);
}
