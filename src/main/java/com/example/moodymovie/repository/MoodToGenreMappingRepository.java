package com.example.moodymovie.repository;

import com.example.moodymovie.constants.InputMoods;
import com.example.moodymovie.constants.OutputMoods;
import com.example.moodymovie.entities.MoodToGenreMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoodToGenreMappingRepository extends JpaRepository<MoodToGenreMapping, Long> {
    @Query("""
            SELECT m.suggestedGenres from MoodToGenreMapping m
            WHERE m.inputMood = :inputMood and m.outputMood = :outputMood
            """)
    public String getMovieGenreByMoods(String inputMood, String outputMood);
}
