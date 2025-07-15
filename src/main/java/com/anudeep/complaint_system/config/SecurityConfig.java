package com.anudeep.complaint_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails student = User.withUsername("user")
                .password(passwordEncoder().encode("user123"))
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, student);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/complaints", "/complaints/").permitAll()
                .requestMatchers(HttpMethod.GET, "/complaints/submit").permitAll()
                .requestMatchers(HttpMethod.POST, "/complaints/submit").hasAnyRole("STUDENT", "ADMIN")
                .requestMatchers("/complaints/view").hasAnyRole("STUDENT", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/complaints/update/**").hasRole("ADMIN")
                .requestMatchers("/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin()
                .defaultSuccessUrl("/complaints/", true)
            .and()
            .logout();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
