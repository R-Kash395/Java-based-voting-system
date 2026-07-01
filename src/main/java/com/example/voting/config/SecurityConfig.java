package com.example.voting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disabled for local REST API testing execution
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/admin/**").hasRole("ADMIN")   // Admin only access
                .requestMatchers("/api/vote/**").hasRole("VOTER")    // Voter only access
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); // Uses standard HTTP Basic Authentication prompts

        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        // Updated to use the modern builder pattern with {noop} for local testing
        UserDetails voter1 = User.builder()
                .username("voter1")
                .password("{noop}password123") 
                .roles("VOTER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin456")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(voter1, admin);
    }
}