package com.example.moodymovie.controller.response;

import com.example.moodymovie.dto.MovieDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MovieResponsePaginated {
    private long page;
    private List<MovieDTO> results;
    @JsonProperty("total_pages")
    private long totalPages;
    @JsonProperty("total_results")
    private long totalResults;
}

