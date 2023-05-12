package ua.nure.akolovych.discoverworld.dto;

import lombok.Data;

@Data
public class RecommendedToursDto {

    String [] tags;

    Integer minTagHits;
}
