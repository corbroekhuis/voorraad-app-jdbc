package com.warehouse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class BasicAuthConfig {

    String userUsername;
    String userPassword;
    String adminUsername;
    String adminPassword;

    @Autowired
    public BasicAuthConfig(
                    @Value("${user.username}") String userUsername,
                    @Value("${user.password}") String userPassword,
                    @Value("${admin.username}") String adminUsername,
                    @Value("${admin.password}") String adminPassword
                ) {
        this.userUsername = userUsername;
        this.userPassword = userPassword;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 =
                User.builder()
                        .username(userUsername)
                        .password(passwordEncoder().encode(userPassword))
                        .roles("USER")
                        .build();
        UserDetails user2 =
                User.builder()
                        .username(adminUsername)
                        .password(passwordEncoder().encode(adminPassword))
                        .roles("USER", "ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.headers(header -> header.frameOptions(options -> options.sameOrigin()));
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/"
                                , "/h2-console/**"
                                , "/v3/api-docs"
                                , "/configuration/ui"
                                , "/swagger-resources/**"
                                , "/configuration/security"
                                , "/swagger-ui/**"
                                , "/swagger-ui.html"
                                , "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/api").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(withDefaults());

        return http.build();
    }
}
