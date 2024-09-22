package com.mood.movie.recommendation.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getMoviesByMoodAndPlatform(String mood, String platform) {
        return movieRepository.findByMoodAndPlatform(mood, platform);
    }
}
