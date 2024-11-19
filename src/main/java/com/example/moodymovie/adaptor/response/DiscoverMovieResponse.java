package com.example.moodymovie.adaptor.response;

import com.example.moodymovie.dto.MovieDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscoverMovieResponse {
    @JsonProperty("page")
    private long page;
    @JsonProperty("results")
    private List<MovieDTO> movieDtoList;
    @JsonProperty("total_pages")
    private long totalPages;
    @JsonProperty("total_results")
    private long totalResults;
}