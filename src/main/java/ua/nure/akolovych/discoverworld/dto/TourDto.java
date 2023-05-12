package ua.nure.akolovych.discoverworld.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ua.nure.akolovych.discoverworld.entity.TourCategoryEntity;

import java.time.Instant;
import java.util.UUID;

@Data
public class TourDto {
    private UUID id;


    private String name;


    private Double price;


    private Long startDate;


    private Long endDate;

    private byte [] tourImage;


    private String description;


    private Double rating;


    private Integer numberOfTours;


    private UUID tourCategoryId;
}
