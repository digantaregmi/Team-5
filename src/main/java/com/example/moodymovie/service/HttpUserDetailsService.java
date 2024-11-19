package com.example.moodymovie.service;

import com.example.moodymovie.dto.HttpUserDetailsDTO;
import com.example.moodymovie.entities.User;
import com.example.moodymovie.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class HttpUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public HttpUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public HttpUserDetailsDTO loadUserByUsername(final String username) {
        final User user = userRepository.findByEmailIgnoreCase(username);
        if (user == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        return new HttpUserDetailsDTO(user.getId(), username, user.getHash(), roles);
    }

}