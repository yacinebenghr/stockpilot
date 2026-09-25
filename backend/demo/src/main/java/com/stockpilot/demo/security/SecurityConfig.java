package com.stockpilot.demo.security;

import com.stockpilot.demo.model.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
       SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            http.csrf(csrf -> csrf.disable());
            http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.formLogin(form -> form.disable());
            http.httpBasic(httpBasic -> httpBasic.disable());

            //premit
            http.authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/api/auth/**").permitAll()
                            .requestMatchers("/api/alerts/**").hasRole(Role.ADMIN.toString())
                            .anyRequest().authenticated()
            );
            return http.build();
       }


}
