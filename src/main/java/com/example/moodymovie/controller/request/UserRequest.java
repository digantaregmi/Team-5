package com.example.moodymovie.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This req will only be used as input for genre recommendation logic
 *
 * **/
@Getter
@Setter
public class UserRequest {
    //todo session work
//    private String userID;
    private String inputMood;
    private String outputMood;
    private int maxWatchTime;
    private int age;
    private List<String> streamingPlatforms;
    private int page = 1;
}