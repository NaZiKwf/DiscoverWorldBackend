package ua.nure.akolovych.discoverworld.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthWhiteList {
    @Bean
    @Qualifier("authWhiteList")
    public String[] getAuthWhiteList() {
        return new String[]{
                "/swagger-resources",
                "/documentation/swagger-ui",
                "/swagger-resources/**",
                "/swagger-resources/configuration/ui",
                "/swagger-resources/configuration/security",
                "/swagger-ui",
                "/swagger-ui/index.html",
                "/swagger-ui/**",
                "/favicon.ico",
                "/error",
                "/webjars/**",
                "/v3/api-docs",
                "/v2/api-docs",
                "/swagger-ui.html",
                "/api/swagger-ui.html",
                "/api/auth/**",
                "/api/comments/get-all/comments/**",
                "/api/comments/get/**",
                "/api/bookings/delete/**",
                "/api/bookings/get-all/accepted-booking-by-user/**",
                "/api/bookings/add/additional-services/**",
                "/api/bookings/get/**",
                "/api/bookings/createBooking/**",
                "/api/additional-services/get-all/additional-services/**",
                "/api/tags/get-all",
                "/api/tours/get/**",
                "/api/tours/get-all/**",
                "/api/user/get/**",
                "/api/categories/get/**",
                "/api/categories/get-all",
                "/api/tags/get/all-tags-from-tour/**",
                "/api/comments/delete/**",
                "/api/bookings/get-all/**"
        };
    }
}
