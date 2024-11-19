package com.example.moodymovie.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MovieWatchProvidersDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("results")
    private Map<String, CountryWatchProviders> results;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CountryWatchProviders {
        @JsonProperty("link")
        private String link;

        @JsonProperty("rent")
        private List<Provider> rent;

        @JsonProperty("buy")
        private List<Provider> buy;

        @JsonProperty("flatrate")
        private List<Provider> flatrate;

    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Provider {
        @JsonProperty("logo_path")
        private String logoPath;

        @JsonProperty("provider_id")
        private int providerId;

        @JsonProperty("provider_name")
        private String providerName;

        @JsonProperty("display_priority")
        private int displayPriority;
    }
}