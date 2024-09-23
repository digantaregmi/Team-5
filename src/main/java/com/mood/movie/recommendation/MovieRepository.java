package com.mood.movie.recommendation;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByMoodAndPlatform(String mood, String platform);
}
