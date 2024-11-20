package com.example.moodymovie.dto;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class StreamingPlatformAndLinkDTO {
    private String link;
    private Optional<List<MovieWatchProvidersDTO.Provider>> providers;
}

