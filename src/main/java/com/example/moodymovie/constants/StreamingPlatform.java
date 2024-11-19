package com.example.moodymovie.constants;

public enum StreamingPlatform {
    NETFLIX("Netflix"),
    AMAZON_PRIME_VIDEO("Amazon Prime Video"),
    APPLE_TV("Apple TV"),
    MAX("Max"),
    GOOGLE_PLAY_MOVIES("Google Play Movies"),
    YOUTUBE("YouTube"),
    RAKUTEN_TV("Rakuten TV"),
    CINEPLEX("Cineplex");

    private final String value;

    StreamingPlatform(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}