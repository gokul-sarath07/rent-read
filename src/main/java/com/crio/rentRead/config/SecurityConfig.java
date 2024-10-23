package com.crio.rentRead.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.crio.rentRead.config.PathConstants.*;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, REGISTER_USER).permitAll()
                        .requestMatchers(HttpMethod.POST, REGISTER_BOOK).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, GET_ALL_USER).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, USER_BASE_PATH + "/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, USER_BASE_PATH + "/*/role").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, GET_ALL_BOOK).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, BOOK_BASE_PATH + "/*").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, BOOK_BASE_PATH + "/*/rent").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, BOOK_BASE_PATH + "/*/return").hasAnyRole("USER", "ADMIN")
                        .anyRequest().denyAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
