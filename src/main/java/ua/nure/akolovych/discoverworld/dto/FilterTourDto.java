package ua.nure.akolovych.discoverworld.dto;

import lombok.Data;

@Data
public class FilterTourDto {

    private String filterQuery;
    private Boolean filterName;
    private Boolean filterDescription;
    private Boolean filterCategory;
    private Boolean filterTags;
}
