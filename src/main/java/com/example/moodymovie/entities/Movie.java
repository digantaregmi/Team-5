package com.example.moodymovie.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie")
public class Movie {

    @Column(name = "adult", nullable = false)
    private boolean adult;

    @Column(name = "backdrop_path", length = 255)
    private String backdropPath;

    @Column(name = "genre_ids", length = 255)
    private String genreIds;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "original_language", length = 10)
    private String originalLanguage;

    @Column(name = "original_title", length = 255)
    private String originalTitle;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @Column(name = "popularity")
    private double popularity;

    @Column(name = "poster_path", length = 255)
    private String posterPath;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "video", nullable = false)
    private boolean video;

    @Column(name = "vote_average")
    private double voteAverage;

    @Column(name = "vote_count")
    private int voteCount;

    @Column(name = "streaming_platforms", columnDefinition = "TEXT")
    private String streamingPlatforms;

    @Column(name = "length")
    private double length;

    @Column(name = "link")
    private String link;
}
