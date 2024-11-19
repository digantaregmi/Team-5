package com.example.moodymovie.utils;

import com.example.moodymovie.constants.Genres;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class CommonUtils {
    @Bean
    public static String loadGenres(List<String> genres) {
        StringBuilder sb = new StringBuilder();
//        genres.forEach(genre -> {
//            if (!sb.isEmpty() && ) {
//                sb.append("%2C");
//            }
//            sb.append(codeFromGenre(genre));
////            sb.append(codeFromGenre(genre)).append("%2C");
//        }
//        for (int i = 0; i < genres.size(); i++) {
//            sb.append(codeFromGenre(genres.get(i)));
//            if (i < genres.size() - 1) {
//                sb.append("%2C");
//            }
//        }
//        return sb.toString();\
        return genres.stream()
                .map(CommonUtils::codeFromGenre)
                .map(String::valueOf)
                .collect(Collectors.joining("%2C"));
    }

    public static int codeFromGenre(String genre) {
        try {
            var map = Genres.toMap();
            return map.get(genre);
        } catch (Exception e) {
            log.error("codeFromGenre(): {}", e.getMessage());
            throw new RuntimeException("Some error occurred", e);
        }

    }
}
