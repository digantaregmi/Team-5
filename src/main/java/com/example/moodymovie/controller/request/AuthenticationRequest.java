package com.example.moodymovie.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthenticationRequest {
    @NotEmpty
    @Size(max = 255)
    private String email;

    @NotEmpty
    @Size(max = 255)
    private String password;
}