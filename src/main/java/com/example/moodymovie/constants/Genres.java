package com.example.moodymovie.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Genres {
    ACTION(28, "action"),
    ADVENTURE(12, "adventure"),
    ANIMATION(16, "animation"),
    COMEDY(35, "comedy"),
    CRIME(80, "crime"),
    DOCUMENTARY(99, "documentary"),
    DRAMA(18, "drama"),
    FAMILY(10751, "family"),
    FANTASY(14, "fantasy"),
    HISTORY(36, "history"),
    HORROR(27, "horror"),
    MUSIC(10402, "music"),
    MYSTERY(9648, "mystery"),
    ROMANCE(10749, "romance"),
    SCIENCE_FICTION(878, "science fiction"),
    TV_MOVIE(10770, "tv movie"),
    THRILLER(53, "thriller"),
    WAR(10752, "war"),
    WESTERN(37, "western");

    private final int id;
    private final String name;

    public static Map<String, Integer> toMap() {
        return Stream.of(Genres.values())
                .collect(Collectors.toMap(Genres::getName, Genres::getId));
    }
}