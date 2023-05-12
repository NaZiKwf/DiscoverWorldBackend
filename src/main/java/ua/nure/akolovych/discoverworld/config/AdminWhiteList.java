package ua.nure.akolovych.discoverworld.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminWhiteList {
    @Bean
    @Qualifier("adminWhiteList")
    public String[] getAdminWhiteList() {
        return new String[] {
                "/api/swagger-ui.html",
                "/api/tours/create",
                "/api/tours/update",
                "/api/tours/delete",
                "/api/tours/update/tour-picture-profile/**",
                "/api/tours/add/tag/**",
                "/api/categories/**",
                "/api/tags/create/",
                "/api/tags/delete/**",
                "/api/comments/delete/**",
                "/api/bookings/update/bookingStatus/**",
                "/api/additional-services/create",
                "/api/additional-services/delete/**",
                "/api/tours/delete/tag-from-tour/**"
        };
    }
}
