package com.example.blogplatform.security;

import com.example.blogplatform.domain.entities.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of Spring Security's {@link UserDetails} interface
 * that wraps a domain {@link User} entity.
 *
 * <p>This class is used by Spring Security during the authentication process
 * to retrieve user-specific data (such as username, password, and authorities).
 *
 * <p>Currently, all authenticated users are assigned a default role of {@code ROLE_USER}.
 * Additional roles or permissions can be added as needed.
 *
 * <p>The account is always considered active, unlocked, and non-expired.
 */
@Getter
@RequiredArgsConstructor
public class BlogUserDetails implements UserDetails {

    private final User user;

    /**
     * Returns the authorities granted to the user.
     * <p>
     * In this implementation, all users are granted a single authority: {@code ROLE_USER}.
     *
     * @return a collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Returns the user's password for authentication.
     *
     * @return the hashed password stored in the {@link User} entity
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     * <p>
     * In this implementation, the user's email is used as the username.
     *
     * @return the email of the user
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Indicates whether the user's account has expired.
     * <p>
     * Always returns {@code true}, meaning the account is never expired.
     *
     * @return {@code true}
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * <p>
     * Always returns {@code true}, meaning the account is never locked.
     *
     * @return {@code true}
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * <p>
     * Always returns {@code true}, meaning the credentials are never expired.
     *
     * @return {@code true}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * <p>
     * Always returns {@code true}, meaning the user is always enabled.
     * This can be extended in the future to support account activation or banning.
     *
     * @return {@code true}
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public UUID getId() {
        return user.getId();
    }
}
