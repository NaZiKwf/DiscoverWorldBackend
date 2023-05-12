package ua.nure.akolovych.discoverworld.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.nure.akolovych.discoverworld.dto.*;
import ua.nure.akolovych.discoverworld.entity.*;

import java.time.Instant;
import java.util.Objects;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.modelmapper.convention.MatchingStrategies.STRICT;

@Configuration
public class ModelMapperConfiguration {

    private final ModelMapper mapper = new ModelMapper();

    private final Converter<Instant, Long> instantToLong =
            ctx -> Objects.isNull(ctx.getSource()) ? null : ctx.getSource().toEpochMilli();

    @Bean
    public ModelMapper modelMapper() {
        mapper.getConfiguration()
                .setMatchingStrategy(STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);


        configureAdditionalServicesEntityToDto();
        configureBookingEntityToDto();
        configureTagEntityToDto();
        configureTourCategoryEntityToDto();
        configureTourEntityToDto();
        configureUserEntityToDto();
        configureFeedbackCommentsEntityToDto();

        return mapper;
    }

    private void configureAdditionalServicesEntityToDto() {
        mapper.typeMap(AdditionalServicesEntity.class, AdditionalServicesDto.class)
                .addMapping(additionalServicesEntity -> additionalServicesEntity.getTour().getId(),
                        AdditionalServicesDto :: setTourId);
    }

    private void configureBookingEntityToDto() {
        mapper.typeMap(BookingEntity.class, BookingDto.class)
                .addMapping(bookingEntity -> bookingEntity.getTour().getId(),
                        BookingDto :: setTourId)
                .addMapping(bookingEntity -> bookingEntity.getUser().getId(),
                        BookingDto :: setUserId)
                .addMappings(expression -> expression.using(instantToLong)
                        .map(BookingEntity:: getDateOfBooking,
                                BookingDto :: setDateOfBooking))
                .addMappings(expression -> expression.using(instantToLong)
                        .map(BookingEntity:: getDateOfBirth,
                                BookingDto :: setDateOfBirth))
                .addMappings(expression -> expression.using(instantToLong)
                        .map(BookingEntity:: getPassportValidUntil,
                                BookingDto :: setPassportValidUntil));
    }

    private void configureFeedbackCommentsEntityToDto(){
        mapper.typeMap(FeedbackCommentsEntity.class, FeedbackCommentsDto.class)
                .addMapping(feedbackCommentsEntity -> feedbackCommentsEntity.getUser().getId(),
                        FeedbackCommentsDto :: setUserId)
                .addMapping(feedbackCommentsEntity -> feedbackCommentsEntity.getTour().getId(),
                        FeedbackCommentsDto :: setTourId)
                .addMappings(expression -> expression.using(instantToLong)
                        .map(FeedbackCommentsEntity :: getCreated,
                                FeedbackCommentsDto :: setCreated))
                .addMappings(expression -> expression.using(instantToLong)
                        .map(FeedbackCommentsEntity :: getUpdated,
                                FeedbackCommentsDto :: setUpdated));
    }

    private void configureTagEntityToDto(){
        mapper.typeMap(TagEntity.class, TagDto.class);
    }

    private void configureTourCategoryEntityToDto(){
        mapper.typeMap(TourCategoryEntity.class, TourCategoryDto.class);
    }

    private void configureTourEntityToDto(){
        mapper.typeMap(TourEntity.class, TourDto.class)
                .addMapping(tourEntity -> tourEntity.getTourCategory().getId(),
                        TourDto :: setTourCategoryId)
                .addMappings(expression -> expression.using(instantToLong)
                        .map(TourEntity :: getStartDate, TourDto :: setStartDate))
                .addMappings(expression -> expression.using(instantToLong)
                        .map(TourEntity :: getEndDate, TourDto :: setEndDate));
    }

    private void configureUserEntityToDto(){
        mapper.typeMap(UserEntity.class, UserDto.class)
                .addMappings(expression -> expression.using(instantToLong)
                        .map(UserEntity :: getCreated, UserDto :: setCreated))
                .addMappings(expression -> expression.using(instantToLong)
                        .map(UserEntity :: getUpdated, UserDto :: setUpdated));
    }

}



