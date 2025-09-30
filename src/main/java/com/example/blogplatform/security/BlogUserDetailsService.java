package com.example.blogplatform.security;

import com.example.blogplatform.domain.entities.User;
import com.example.blogplatform.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}
 * that loads user-specific data from the application's database using {@link UserRepository}.
 *
 * <p>This service is used by Spring Security during the authentication process
 * to retrieve the user associated with the given email (used as username).
 *
 * <p>If the user is found, it is wrapped in a {@link BlogUserDetails} object and returned.
 * If not found, a {@link UsernameNotFoundException} is thrown.
 */
@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads the user details by email address.
     *
     * @param email the email address of the user attempting to authenticate
     * @return a {@link UserDetails} implementation containing the user's information
     * @throws UsernameNotFoundException if the user with the given email does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found."));
        return new BlogUserDetails(user);
    }
}
