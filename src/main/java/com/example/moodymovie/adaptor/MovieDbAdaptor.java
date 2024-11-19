package com.example.moodymovie.adaptor;

import com.example.moodymovie.adaptor.response.DiscoverMovieResponse;
import com.example.moodymovie.dto.MovieDetailsDTO;
import com.example.moodymovie.dto.MovieWatchProvidersDTO;
import com.example.moodymovie.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class MovieDbAdaptor {

    @Value("${movie.db.url}")
    private String baseUrl;
    @Value("${movie.db.url.api.version}")
    private String apiVersion;
    @Value("${movie.db.api.key}")
    private String apiKey;
//    @Value("${movie.db.bearer.token}")
//    private String token;

    // discover -> movie
    public DiscoverMovieResponse getMovieListByGenres(List<String> genres, long page) {
        try {
            HttpResponse<String> response;
            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                String url = String.format("%s/%s/discover/movie?api_key=%s&page=%s&with_genres=%s",
                        baseUrl, apiVersion, apiKey, page, CommonUtils.loadGenres(genres));
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("accept", "application/json")
                        .GET()
                        .build();
                log.info("getMovieListByGenres() url: {}", url);
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }
            DiscoverMovieResponse discoverMovieResponse = new DiscoverMovieResponse();
            if (!ObjectUtils.isEmpty(response.body())) {
                ObjectMapper objectMapper = new ObjectMapper();
                discoverMovieResponse = objectMapper.readValue(response.body(), DiscoverMovieResponse.class);
                discoverMovieResponse.getMovieDtoList().forEach(movieDetail -> {
                    System.out.println(movieDetail.getOriginalTitle());
                });
            }
            return discoverMovieResponse;

        } catch (Exception e) {
            log.error("getMovieListByGenres() ERROR {}", e.getMessage());
            throw new RuntimeException("Failed to get movie list by gerne", e);
        }
    }

    public int getWatchTime(long movieId) {
        try {
            HttpResponse<String> response;
            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                String url = String.format("%s/%s/movie/%s?api_key=%s",
                        baseUrl, apiVersion, movieId, apiKey);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("accept", "application/json")
                        .GET()
                        .build();
                log.info("getWatchTime() url: {}", url);
                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }
            if (!ObjectUtils.isEmpty(response.body())) {
                ObjectMapper objectMapper = new ObjectMapper();
                MovieDetailsDTO movieDetailsDTO = objectMapper.readValue(response.body(), MovieDetailsDTO.class);
                return movieDetailsDTO.getRuntime();
            }
        } catch (Exception e) {
            log.error("getWatchTime() ERROR: {}", e.getMessage());
            throw new RuntimeException("Failed to get runtime", e);
        }
        return 0;
    }

    public List<String> getStreamingPlatforms(long movieId) {
        try {
            HttpResponse<String> response;
            try (HttpClient httpClient = HttpClient.newHttpClient()) {
                String url = String.format("%s/%s/movie/%s/watch/providers?api_key=%s",
                        baseUrl, apiVersion, movieId, apiKey);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("accept", "application/json")
                        .GET()
                        .build();
                log.info("getWatchTime() url: {}", url);

                response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            }
//            if (!ObjectUtils.isEmpty(response.body())) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                MovieWatchProvidersDTO movieWatchProvidersDTO = objectMapper.readValue(response.body(), MovieWatchProvidersDTO.class);
//                return movieWatchProvidersDTO.getResults().get("US").getBuy().stream()
//                        .map(MovieWatchProvidersDTO.Provider::getProviderName).toList();
            if (!ObjectUtils.isEmpty(response.body())) {
                ObjectMapper objectMapper = new ObjectMapper();
                MovieWatchProvidersDTO movieWatchProvidersDTO = objectMapper.readValue(response.body(), MovieWatchProvidersDTO.class);
                return Optional.ofNullable(movieWatchProvidersDTO.getResults().get("US"))
                        .map(countryWatchProviders -> {
                            List<MovieWatchProvidersDTO.Provider> providers = countryWatchProviders.getBuy();
                            if (providers == null || providers.isEmpty()) {
                                providers = countryWatchProviders.getFlatrate();
                            }
                            return providers;
                        })
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(MovieWatchProvidersDTO.Provider::getProviderName)
                        .toList();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("getStreamingPlatforms() ERROR: {}", e.getMessage());
            throw new RuntimeException("Unable to get streaming platform", e);
        }
    }
}
