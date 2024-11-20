package com.example.moodymovie.service;
import com.example.moodymovie.adaptor.MovieDbAdaptor;
import com.example.moodymovie.adaptor.response.DiscoverMovieResponse;
import com.example.moodymovie.controller.request.UserRequest;
import com.example.moodymovie.controller.response.SingleMovieResponse;
import com.example.moodymovie.dto.MovieWatchProvidersDTO;
import com.example.moodymovie.dto.StreamingPlatformAndLinkDTO;
import com.example.moodymovie.entities.Movie;
import com.example.moodymovie.repository.MoodToGenreMappingRepository;
import com.example.moodymovie.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieDbAdaptor movieDbAdaptor;
    private final MoodToGenreMappingRepository moodToGenreMappingRepository;
    private final MovieRepository movieRepository;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Transactional
    public String filterAndSaveMovie(UserRequest userRequest) {
        try {
            // get recommended genres from db
            var genresString = moodToGenreMappingRepository.getMovieGenreByMoods(userRequest.getInputMood(),
                    userRequest.getOutputMood());
            List<String> genres = Arrays.stream(genresString.split(","))
                    .map(String::trim)
                    .toList();

            // 1 page contains 20 data.
            DiscoverMovieResponse discoverMovieResponse = movieDbAdaptor.getMovieListByGenres(genres, userRequest.getPage());

            var res = discoverMovieResponse.getMovieDtoList().stream()
                    .filter(movie -> userRequest.getAge() >= 18 || !movie.isAdult()) // filter out adult movies
                    .filter(movie -> movieDbAdaptor.getWatchTime(movie.getMovieId()) <= userRequest.getMaxWatchTime()) // filter based on watch time
                    .map(movieDTO -> {
                        // Create a new Movie entity
                        Movie movie = new Movie();
                        movie.setId(movieDTO.getMovieId());
                        movie.setAdult(movieDTO.isAdult());
                        movie.setBackdropPath(movieDTO.getBackdropPath());
                        movie.setGenreIds(movieDTO.getGenreIds().toString());
                        movie.setOriginalLanguage(movieDTO.getOriginalLanguage());
                        movie.setOriginalTitle(movieDTO.getOriginalTitle());
                        movie.setOverview(movieDTO.getOverview());
                        movie.setPopularity(movieDTO.getPopularity());
                        movie.setPosterPath(movieDTO.getPosterPath());
                        movie.setReleaseDate(movieDTO.getReleaseDate());
                        movie.setTitle(movieDTO.getTitle());
                        movie.setVideo(movieDTO.isVideo());
                        movie.setVoteAverage(movieDTO.getVoteAverage());
                        movie.setVoteCount(movieDTO.getVoteCount());

                        // Set the additional fields
                        Optional<StreamingPlatformAndLinkDTO> streamingPlatformAndLinkDTOptional = movieDbAdaptor.getStreamingPlatforms(movieDTO.getMovieId());
                        List<MovieWatchProvidersDTO.Provider> streamingProviders = null;
                        List<String> moviePlatforms = new ArrayList<>();
                        if (streamingPlatformAndLinkDTOptional.isPresent()) {
                            var streamingPlatformAndLinkDTO = streamingPlatformAndLinkDTOptional.get();
                            if (streamingPlatformAndLinkDTO.getProviders().isPresent()) {
                                streamingProviders = streamingPlatformAndLinkDTO.getProviders().get();
                                if (!CollectionUtils.isEmpty(streamingProviders)) {
                                    moviePlatforms = streamingProviders.stream()
                                            .map(MovieWatchProvidersDTO.Provider::getProviderName)
                                            .toList();
                                    movie.setStreamingPlatforms(String.join(",", moviePlatforms));
                                    movie.setLength(movieDbAdaptor.getWatchTime(movieDTO.getMovieId()));
                                    movie.setLink(streamingPlatformAndLinkDTO.getLink());
                                }
                            }
                        }

                        return new AbstractMap.SimpleEntry<>(movie, moviePlatforms);
                    })
                    .filter(entry -> userRequest.getStreamingPlatforms().stream()
                            .anyMatch(userPlatform -> entry.getValue().stream()
                                    .anyMatch(mp -> mp.equalsIgnoreCase(userPlatform))))
                    .map(Map.Entry::getKey)
                    .toList();
//            System.out.println("Hello");
            //TODO need to delete all rows before saving new one
            // treating this as cache. Will implement other solutions later on if required.
            movieRepository.deleteAll();
            movieRepository.saveAll(res);
            return "success";
        } catch (Exception e) {
            log.error("filterAndSaveMovie() ERROR: {}", e.getMessage());
            throw new RuntimeException("Unable to sync movie", e);
        }
    }

    public Page<SingleMovieResponse> getSingleMoviePaginated(Pageable pageable) {
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<SingleMovieResponse> singleMovieResponses = moviePage.getContent().stream()
                .map(movie -> new SingleMovieResponse(
                        movie.getPosterPath(),
                        String.valueOf(movie.getId()),
                        movie.getTitle(),
                        movie.getOverview(),
                        movie.getLink()
                ))
                .toList();
        return new PageImpl<>(singleMovieResponses, pageable, moviePage.getTotalElements());
    }
}