package ua.nure.akolovych.discoverworld.dto;


import lombok.Data;
import ua.nure.akolovych.discoverworld.enumeration.AdditionalServicesType;

import java.util.UUID;

@Data
public class AdditionalServicesDto {
    private UUID id;

    private String name;

    private Double price;

    private AdditionalServicesType additionalServicesType;

    private UUID tourId;
}
