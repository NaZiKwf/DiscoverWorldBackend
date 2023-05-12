package ua.nure.akolovych.discoverworld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.akolovych.discoverworld.entity.FeedbackCommentsEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackCommentsRepository extends JpaRepository <FeedbackCommentsEntity, UUID> {
    List<FeedbackCommentsEntity> findAllCommentsByTourId(UUID tourId);
}
