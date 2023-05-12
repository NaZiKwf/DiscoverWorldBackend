package ua.nure.akolovych.discoverworld.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ua.nure.akolovych.discoverworld.security.jwt.JwtConfigurer;
import ua.nure.akolovych.discoverworld.security.jwt.JwtTokenProvider;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    private final RestAuthenticationEntryPoint authenticationEntryPoint;

    private final String [] adminWhiteList;
    private final String[] authWhiteList;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider,
                                 RestAuthenticationEntryPoint authenticationEntryPoint,
                                 @Qualifier("authWhiteList") String[] authWhiteList,
                                 @Qualifier("adminWhiteList") String [] adminWhiteList)
    {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authWhiteList = authWhiteList;
        this.adminWhiteList = adminWhiteList;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .antMatchers(authWhiteList).permitAll()
                        .antMatchers(adminWhiteList).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider, authenticationEntryPoint))
                .and()
                .build();
    }
}
