package ua.nure.akolovych.discoverworld.dto;


import lombok.Data;


import java.util.UUID;

@Data
public class FeedbackCommentsDto {
    private UUID id;

    private String comment;

    private Double estimateOfTour;

    private Long created;


    private Long updated;


    private UUID userId;

    private UUID tourId;
}
