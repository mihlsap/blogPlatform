package com.example.blogplatform.config;

import com.example.blogplatform.domain.entities.User;
import com.example.blogplatform.repositories.UserRepository;
import com.example.blogplatform.security.BlogUserDetailsService;
import com.example.blogplatform.security.JwtAuthenticationFilter;
import com.example.blogplatform.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up Spring Security for the blog platform.
 * <p>
 * This configuration:
 * <ul>
 *     <li>Permits all GET requests to public blog endpoints (posts, categories, tags).</li>
 *     <li>Requires authentication for all other endpoints.</li>
 *     <li>Disables CSRF protection (suitable for stateless APIs).</li>
 *     <li>Configures the application to use stateless session management.</li>
 *     <li>Exposes beans for {@link PasswordEncoder} and {@link AuthenticationManager}.</li>
 * </ul>
 */
@Configuration
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationService authenticationService) {
        return new JwtAuthenticationFilter(authenticationService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        BlogUserDetailsService blogUserDetailsService = new BlogUserDetailsService(userRepository);

        String email = "user@test.com";
        userRepository.findByEmail(email).orElseGet(() -> userRepository.save(User.builder()
                .name("Test User")
                .email(email)
                .password(passwordEncoder().encode("password"))
                .build()
        ));
        return blogUserDetailsService;
    }

    /**
     * Defines the security filter chain used by Spring Security.
     * <p>
     * This filter chain:
     * <ul>
     *     <li>Allows unauthenticated access to certain GET endpoints.</li>
     *     <li>Requires authentication for all other requests.</li>
     *     <li>Disables CSRF protection (since the API is stateless).</li>
     *     <li>Configures stateless session management to avoid using HTTP sessions.</li>
     * </ul>
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception in case of any configuration errors
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/tags/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Defines a {@link PasswordEncoder} bean.
     * <p>
     * Uses a delegating password encoder which supports multiple encoding formats
     * (e.g., bcrypt, PBKDF2, etc.) and provides backward compatibility with legacy encodings.
     *
     * @return a delegating {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Exposes the {@link AuthenticationManager} from the provided {@link AuthenticationConfiguration}.
     * <p>
     * This allows for manual authentication in services (e.g., for login endpoints).
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the application's {@link AuthenticationManager}
     * @throws Exception if the authentication manager cannot be retrieved
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
