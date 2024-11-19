package com.example.moodymovie.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "mood_to_genre_mapping")
public class MoodToGenreMapping {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "input_mood", nullable = false, length = 50)
    private String inputMood;

    @Column(name = "output_mood", nullable = false, length = 50)
    private String outputMood;

    @Column(name = "suggested_genres", nullable = false, length = 255)
    private String suggestedGenres;

    @Column(name = "rationale", columnDefinition = "TEXT")
    private String rationale;
}
