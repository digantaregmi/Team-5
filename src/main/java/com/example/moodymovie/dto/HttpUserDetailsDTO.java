package com.example.moodymovie.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
public class HttpUserDetailsDTO implements UserDetails {

    private final long id;
    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
}