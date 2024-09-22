package com.mood.movie.recommendation.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/recommend")
    public List<Movie> getMovies(@RequestParam String mood, @RequestParam String platform) {
        return movieService.getMoviesByMoodAndPlatform(mood, platform);
    }
}
