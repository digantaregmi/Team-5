package com.example.moodymovie.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SingleMovieResponse {
    private String posterLink;
    private String movieId;
    private String movieTitle;
    private String movieOverview;
    private String link;
}
