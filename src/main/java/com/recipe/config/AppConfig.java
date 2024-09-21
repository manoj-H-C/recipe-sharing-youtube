package com.recipe.config;

import com.recipe.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.mapping.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
public class AppConfig {
    private CustomUserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtTokenValidator jwtAuthenticationFilter;

    public AppConfig(CustomUserDetailsService userDetailsService, JwtTokenValidator jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    //    basic authentication
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf)->csrf.disable())
                .authorizeHttpRequests((authorize)->
//                        authorize.anyRequest().authenticated()
                                authorize.requestMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
                                        .requestMatchers("/auth/**").permitAll()
//                                        .requestMatchers("/auth/**").permitAll()
                                        .requestMatchers("/v3/api-docs/**").permitAll()
                                        .anyRequest().authenticated()
                ).exceptionHandling( exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                ).sessionManagement( session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
//        for jwt
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
